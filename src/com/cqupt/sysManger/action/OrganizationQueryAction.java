package com.cqupt.sysManger.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class OrganizationQueryAction extends ActionSupport{

/**
	 * 
	 */
	private static final long serialVersionUID = 1794195924380050122L;
	HttpServletRequest request=null;

	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}

	
	public String execute()
	{	
//System.out.println("67811");
		String deptId="";

		this.request = ServletActionContext.getRequest(); 
		deptId = (String)request.getParameter("deptId");
	    String str = new String(getChildDept(deptId)) ;
	    try {
			inputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return SUCCESS;


	}
	
	
	private String getChildDept(String deptId) {
		DataStormSession session = null;
		StringBuilder resultStr = new StringBuilder();
		String sql="";
		if(deptId.equals("init")) {
			//若为初始化，则根据权限判断deptId
			deptId = "1";
		} 
		try 
		{
			session = DataStormSession.getInstance();
			int childdeptIdLength = 3;
		
		    if (deptId.length()==3){
				childdeptIdLength = deptId.length()+2;
		    }
			System.out.println("dept_id = "+deptId);
		    if(deptId.equals("2")){
		    	sql="SELECT a.*,ifnull(child_total,0) child_total FROM (select * from cqmass.sys_dept t WHERE t.parent_dept_id = '"+deptId+"'  ORDER BY t.dept_id) a left join"+         //选出一级菜单
				   "(SELECT parent_dept_id,COUNT(*) child_total from cqmass.sys_dept t WHERE t.parent_dept_id = '"+deptId+"' GROUP BY parent_dept_id ) b "+															  //找出所有父节点对应子节点的数量
				   "on a.dept_id = b.parent_dept_id where a.dept_state!='不可用' order by a.dept_name";	
		    }else{
			    if(deptId.length()==1){

			    	 sql="SELECT a.*,ifnull(child_total,0) child_total FROM (select * from cqmass.sys_dept t WHERE t.dept_id LIKE '"+deptId+"%' AND length(t.dept_id)="+childdeptIdLength+" ORDER BY t.dept_id) a left join"+         //选出一级菜单
							   "(SELECT parent_dept_id,COUNT(*) child_total from cqmass.sys_dept t WHERE t.dept_id LIKE '_%' GROUP BY parent_dept_id ) b "+															  //找出所有父节点对应子节点的数量
							   "on a.dept_id = b.parent_dept_id where a.dept_state!='不可用'";
			    }else{
			    	sql="SELECT a.*,ifnull(child_total,0) child_total FROM (select * from cqmass.sys_dept t WHERE t.parent_dept_id LIKE '"+deptId+"%' AND length(t.dept_id)="+childdeptIdLength+" ORDER BY t.dept_id) a left join"+         //选出一级菜单
						   "(SELECT parent_dept_id,COUNT(*) child_total from cqmass.sys_dept t WHERE t.dept_id LIKE '"+deptId+"%' GROUP BY parent_dept_id ) b "+															  //找出所有父节点对应子节点的数量
						   "on a.dept_id = b.parent_dept_id where a.dept_state!='不可用'";		
			    }	
		    }
		    //匹配对应关系
System.out.println("OrganizationQueryAction:"+sql);
			List resultList = session.findSql(sql);
			if((resultList.size())==0)
			{
				resultStr.append("");
			} else {
				for(int i=0;i<resultList.size();i++) {
					Map resultMap = (Map)resultList.get(i);
					if( 0==i ){
						resultStr.append("<?xml version=\"1.0\"?> <tree>");
					}
					if(resultMap.get("childTotal").toString().equals("0")) {
						resultStr.append("<tree text=\""+resultMap.get("deptName")+"\"  action=\"javascript:changeList('"+resultMap.get("deptId")+":"+resultMap.get("deptName")+"')\"/>");
					} else {
						resultStr.append("<tree text=\""+resultMap.get("deptName")+"\" action=\"javascript:changeList('"+resultMap.get("deptId")+":"+resultMap.get("deptName")+"')\" src=\"organizationQueryAction?deptId="+resultMap.get("deptId")+"\"   />");
					}
					
					
					if (i== (resultList.size()-1)) {
						resultStr.append("</tree>");
					}
					
				}
			} 
			session.closeSession();
		}		
		catch (Exception e) 
		{
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
System.out.println(resultStr);
		return resultStr.toString();
	}
	
}
