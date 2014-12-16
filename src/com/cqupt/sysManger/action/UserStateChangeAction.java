package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class UserStateChangeAction  extends ActionSupport{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2019244984615347199L;
	
	HttpServletRequest request = null;

	public String execute() {
		request = ServletActionContext.getRequest();
		String userId = null;
		if(request.getParameter("userId")== null) {
			userId = "";
		} else {
			try {
				userId = java.net.URLDecoder.decode(request.getParameter("userId"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	
System.out.println("userId=" + userId);



		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(changeState(userId));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private char[] changeState(String userId) {
		DataStormSession session = null;
		String resultStr = "";
		try {
			String sql ="update sys_user t set t.user_state = decode(t.user_state,0,1,1,0)  where user_id = '"+userId+"'";
			session = DataStormSession.getInstance();
			session.update(sql);
			session.closeSession();
			resultStr = "success";
		} catch (CquptException e) {
			resultStr = "error";
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} 
		System.out.println(resultStr);
		return resultStr.toCharArray();
	}

}
