package com.cqupt.pub.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.JsonUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class UserGroupQueryAction extends ActionSupport{
Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;
	public String execute(){
		//System.out.println("1231");
//System.out.println("UserGroupQueryActionAction:"+deptId);
			request=ServletActionContext.getRequest();		
			HttpSession session = request.getSession();
			String deptId = (String)session.getAttribute("deptId");
			System.out.println(deptId);
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
			System.out.println(deptId);
			if( deptId.length()== 1){
				sql = "select t.group_id id,t.group_name text from cqmass.sys_user_group t where length(t.group_id)!=2 and t.group_id like '11%' or length(t.group_id)=1 and t.group_id = '"+deptId+"%'";
			}else if( deptId.length()== 3){
				sql = "select t.group_id id,t.group_name text from cqmass.sys_user_group t where length(t.group_id)!=2 and t.group_id like '12%' ";
			}else{
				sql = "select t.group_id id,t.group_name text from cqmass.sys_user_group t where length(t.group_id)!=2 and t.group_id like '13%' ";
				
			}
						
			logger.info("角色组查询:"+sql);
			JSONArray jsonObject = JSONArray.fromObject(session.findSql(sql));
			System.out.println(jsonObject.toString());		
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
