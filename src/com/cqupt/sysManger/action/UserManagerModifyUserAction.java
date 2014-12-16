package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.Md;
import com.opensymphony.xwork2.ActionSupport;

public class UserManagerModifyUserAction extends ActionSupport{
    Logger logger = Logger.getLogger(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = -6941574699319426537L;

	/**
		 * 
		 */
		HttpServletRequest request=null;
		private Md md5fun = new Md();

		public String execute()
		{
			
			

			this.request = ServletActionContext.getRequest();
			
			try {
				String txtUserId = java.net.URLDecoder.decode(request.getParameter("txtUserId"), "UTF-8");
				logger.info(txtUserId);
				String txtName = java.net.URLDecoder.decode(request.getParameter("txtName"), "UTF-8");
				logger.info(txtName);
				
				String txtUserGroup = java.net.URLDecoder.decode(request.getParameter("txtUserGroup"), "UTF-8");
				
				String txtPhone = java.net.URLDecoder.decode(request.getParameter("txtPhone"), "UTF-8");
				String txtEmail = java.net.URLDecoder.decode(request.getParameter("txtEmail"), "UTF-8");
				String isUseable = java.net.URLDecoder.decode(request.getParameter("isUseable"), "UTF-8");
				if(isUseable.equals("1")){
					 isUseable = "可用";
				}else{
					isUseable = "禁用";
				}
				logger.info(isUseable);
				HttpServletResponse response=ServletActionContext.getResponse();
		  
		        response.setCharacterEncoding("UTF-8");    
		        PrintWriter out;
		        out = response.getWriter();
				out.print(insertUserinfo(txtUserId, txtName,  txtUserGroup, txtPhone, txtEmail, isUseable));    
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
		
		
		private String insertUserinfo(String txtUserId, String txtName, String txtUserGroup,String txtPhone, String txtEmail, String isUseable) {
			DataStormSession session = null;
			String resultStr = "";
			String sql= "";
			
			try {
				session = DataStormSession.getInstance();
			
				/*sql = "delete from sys_user t WHERE t.user_id = '"+txtUserId+"'";
				session.delete(sql);*/
				
					
				sql = "update sys_user set user_name ='"+txtName+"',group_name = '"+txtUserGroup+"',user_state='"+isUseable+"',user_email='"+txtEmail+"',phone_num='"+txtPhone+"' where user_id='"+txtUserId+"'";
				logger.info(sql);
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
