package com.cqupt.pub.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class salegadgetsQueryAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;	
	
	public String execute(){
System.out.println("GadgetsQueryAction");
			request=ServletActionContext.getRequest();
		
			String 	versionId = request.getParameter("versionId");
			try {
				versionId = java.net.URLDecoder.decode(versionId, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
System.out.println(versionId);
			
			
			HttpServletResponse response=ServletActionContext.getResponse();
	           //设置字符集    
           response.setCharacterEncoding("UTF-8");    
           PrintWriter out;
			try {
				out = response.getWriter();
				   //直接输入响应的内容    
		        out.println(getList(versionId));    
		        out.flush();    
		        out.close();    
			} catch (IOException e) {
				e.printStackTrace();
			}    

	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

	}
	
	public String getList(String versionId){
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;
		try{
			session = DataStormSession.getInstance();
			sql = "select @row := @row +1 as id,t.* from (SELECT @row:=0) r ,(select distinct b.gadgets_description text from cqmass.pro_version_gadgets a,cqmass.pro_gadgets b where a.gadgets_code = b.gadgets_code and b.gadgets_type_name='附件' and a.version_name='"+versionId+"') t";			
System.out.println(sql);	
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
