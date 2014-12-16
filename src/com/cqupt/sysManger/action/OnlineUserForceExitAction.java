package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cqupt.login.OnlineUser;
import com.opensymphony.xwork2.ActionSupport;

public class OnlineUserForceExitAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -890554702524573395L;
	HttpServletRequest request=null;
	
	public String execute()
	{

		this.request = ServletActionContext.getRequest();
		String userId = "";
		try {
			userId = java.net.URLDecoder.decode(request.getParameter("userId").toString(), "utf-8");
		} catch (UnsupportedEncodingException e) {
	// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpServletResponse response=ServletActionContext.getResponse();
        //设置字符集    
        response.setCharacterEncoding("UTF-8");    
        PrintWriter out;
		try {
			out = response.getWriter();
			out.print(getUserForceExitResult(userId));    
	        out.flush();    
	        out.close();    

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    

        //直接输入响应的内容    
        
		return null;

	}
	
	String getUserForceExitResult(String userId) {
		String id[] = userId.split(";");
		
        for (int i = 0; i<id.length; i++) {
        	if(OnlineUser.online.get(id[i]) != null) {
        		OnlineUser.online.get(id[i]).invalidate();
        	}   	
        }
		return "sucess";
	}
	

}
