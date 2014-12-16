package com.cqupt.pub.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class DeptQueryAction extends ActionSupport{
Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;	
	
	public String execute(){
System.out.println("DeptQueryAction:");
			request=ServletActionContext.getRequest();		
			String deptId = request.getSession().getAttribute("deptId").toString();
			HttpServletResponse response=ServletActionContext.getResponse();
	           //设置字符集    
           response.setCharacterEncoding("UTF-8");    
           PrintWriter out;
			try {
				out = response.getWriter();
				   //直接输入响应的内容    
		        out.println(getList(deptId));    
		        out.flush();    
		        out.close();    
			} catch (IOException e) {
				e.printStackTrace();
			}    

	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

	}
	
	public String getList(String deptId){
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;
		try{
			session = DataStormSession.getInstance();
			sql = "select parent_dept_id from cqmass.sys_dept where dept_id='"+deptId+"'";
			List parentList = session.findSql(sql);
			Map parentMap = (Map)parentList.get(0);
			String parentDeptId = parentMap.get("parentDeptId").toString();
			if(deptId.equals("1") || deptId.equals("2") || deptId.equals("112")){
				//管理部
				sql = "select t.dept_id id,t.dept_name text from cqmass.sys_dept t where t.dept_id not in ('103','2')";			
				
			}else if(parentDeptId.equals("103")){
				//主城接机点
				sql = "select t.dept_id id,t.dept_name text from cqmass.sys_dept t where t.dept_id in ('1','101','102')";			
				
			}else if(deptId.length()==3)
			{
				//（临时）丽苑需要发往所有网点，包括接机点
				/*if(deptId.equals("112")){
					sql = "select dept_id id,dept_name text from  cqmass.sys_dept  where dept_id in (( SELECT dept_id FROM cqmass.sys_dept where parent_dept_id='1' and dept_id <> '103')) union select " +
							" dept_id id, dept_name text from  cqmass.sys_dept where  dept_id in ('10803','10901','10905','11102','10804','10802','11101','10702','10902','10601','10903','10402','10906','10701','10801','11003','10401','10602','10904','11002','10703')" +
							" union select dept_id,dept_name from cqmass.sys_dept where parent_dept_id='"+deptId+"'";
				}else*/
				if(deptId.equals("118")){//涪陵维修中心（唐波网点）需要 发到涪陵维修中心（合）的子网点
					//sql = "select dept_id id,dept_name text from  cqmass.sys_dept  where dept_id in (( SELECT dept_id FROM cqmass.sys_dept where parent_dept_id='1' and dept_id <> '103')) " +
				    sql = "select dept_id id,dept_name text from  cqmass.sys_dept  where dept_id in ( SELECT dept_id FROM cqmass.sys_dept where parent_dept_id in ('1','00') and dept_id <> '103') " +	
					" union select dept_id,dept_name from cqmass.sys_dept where parent_dept_id in ('"+deptId+"','109')";
					}else{
				//8大维修中心能发到自己父亲和孩子网点和    平级
//				sql = "select dept_id id,dept_name text from  cqmass.sys_dept  where dept_id in ( SELECT dept_id FROM cqmass.sys_dept where parent_dept_id='1' and dept_id <> '103') " +
				    sql = "select dept_id id,dept_name text from  cqmass.sys_dept  where dept_id in ( SELECT dept_id FROM cqmass.sys_dept where parent_dept_id in ('1','00') and dept_id <> '103') " +

				" union select dept_id,dept_name from cqmass.sys_dept where parent_dept_id='"+deptId+"'";			
				}
				}else{
					//涪陵有两个区域维修中心，现在涪陵下面的接机点需要发往这两个部门
System.out.println("父部门"+parentDeptId);
					if(parentDeptId.equals("109")){
						sql = "select dept_id id,dept_name text from  cqmass.sys_dept  where dept_id in (( SELECT parent_dept_id FROM cqmass.sys_dept where dept_id='"+deptId+"'),'112') union select " +
								" dept_id id ,dept_name text from cqmass.sys_dept where dept_id = '118' ";
					}else{
						// 县级接机点,只能发到自己的上级网点
						//sql = "select dept_id id,dept_name text from  cqmass.sys_dept  where dept_id=( SELECT parent_dept_id FROM cqmass.sys_dept where dept_id='"+deptId+"')";			
						// (临时)县级接机点,能发到自己的上级网点和丽苑
						sql = "select dept_id id,dept_name text from  cqmass.sys_dept  where dept_id in (( SELECT parent_dept_id FROM cqmass.sys_dept where dept_id='"+deptId+"'),'112')";
					}
							
			}
			logger.info("查询部门:"+sql);
			JSONArray jsonObject = JSONArray.fromObject(session.findSql(sql));
			resultStr = jsonObject.toString();
		} catch (CquptException ce) {
			ce.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.closeSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(resultStr);
		return resultStr;
		
	}

}
