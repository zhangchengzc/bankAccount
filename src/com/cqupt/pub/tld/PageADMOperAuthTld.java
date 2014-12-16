package com.cqupt.pub.tld;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class PageADMOperAuthTld extends ComponentTagSupport{
	private String menuId;
	
	private String userId;
	

	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		// TODO Auto-generated method stub
		this.setUserId(arg1.getSession().getAttribute("userId").toString());
		return new PageADMOperAuth(arg0);
	}
	
	protected void populateParams(){
		super.populateParams();
		PageADMOperAuth PageADMOperAuth = (PageADMOperAuth)component;
		PageADMOperAuth.setMenuId(this.getMenuId());
		PageADMOperAuth.setUserId(this.getUserId());
	}

}
