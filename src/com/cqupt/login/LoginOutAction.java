package com.cqupt.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;



import com.opensymphony.xwork2.ActionSupport;



public class LoginOutAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;
	public String execute()
	{
		this.request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();    
		if (session != null) {
	         session.invalidate();//退出登录时，销毁session
		}
    return null;
	}
}