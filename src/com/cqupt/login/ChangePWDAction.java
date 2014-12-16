package com.cqupt.login;

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

public class ChangePWDAction extends ActionSupport{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7613471597036345476L;
	/**
		 * 
		 */
		HttpServletRequest request=null;

		
		private Md md5fun = new Md();
		String resultMsg = null;
		
		public String execute()
		{
			

			this.request = ServletActionContext.getRequest();
			
			try {
				String userId = request.getSession().getAttribute("userId").toString();
				String pwd = java.net.URLDecoder.decode(request.getParameter("newpass"), "UTF-8");
				HttpServletResponse response=ServletActionContext.getResponse();
		           //设置字符集    
		        response.setCharacterEncoding("UTF-8");    
		        PrintWriter out;
		        out = response.getWriter();
		      
		       resultMsg = updatePWD(userId, pwd);
		       if(resultMsg == "success")
		    	   out.print(pwd);    
		        out.flush();    
		        out.close();  
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  catch (Exception e) {
				e.printStackTrace();
			} 
	           //直接输入响应的内容    	        
	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    


		}
		
		
		private String updatePWD(String userId, String pwd) {
			DataStormSession session = null;
			String resultStr = "";
			String sql= "";
			String id = "";
			try {
				session = DataStormSession.getInstance();
				
				
			    sql = "update sys_user t set t.user_pwd='"+md5fun.getMD5ofStr(pwd)+"' where t.user_id ='"+userId+"'";
			    System.out.println(sql);
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

