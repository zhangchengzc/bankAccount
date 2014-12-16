package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.Md;
import com.opensymphony.xwork2.ActionSupport;

public class UserManagerPasswordModifyAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3592801814695320357L;
	/**
		 * 
		 */
		HttpServletRequest request=null;
		private Md md5fun = new Md();

		public String execute()
		{
					

			request = ServletActionContext.getRequest();
			String txtUserId = request.getParameter("txtUserId");
			String txtPsw = "111111";
            HttpServletResponse response=ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");    
	        PrintWriter out;
			
			try {			
			
		    
		       
		        out = response.getWriter();
				out.print(insertUserinfo(txtUserId,txtPsw));    
		        out.flush();    
		        out.close();  
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}    
      
	       return null;


		}
		
		
		private String insertUserinfo(String txtUserId,String txtPsw) {
			DataStormSession session = null;
			String resultStr = "";
			String sql= "";
			
			
			try {
				session = DataStormSession.getInstance();
						
				sql = "update sys_user set user_pwd = '"+md5fun.getMD5ofStr(txtPsw)+"' where user_id = '"+txtUserId+"'" ;
System.out.println("sql="+sql);				
				session.update(sql);
				session.closeSession();
				resultStr = "success";
	
				
				
			}catch (Exception e) {
				resultStr = "error";
				try {
					session.exceptionCloseSession();
				} catch (CquptException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				
			}
			return resultStr;

		}
		
}
