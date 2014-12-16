package com.cqupt.sysManger.tld;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class UserNameTld extends ComponentTagSupport{
	
	private String userName;
	private String deptName;
	
	

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}



	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		// TODO Auto-generated method stub
		this.setUserName(arg1.getSession().getAttribute("userName").toString());
		this.setDeptName(arg1.getSession().getAttribute("deptName").toString());
		return new UserName(arg0);
	}
	
	protected void populateParams(){
		super.populateParams();
		UserName userName = (UserName)component;
		userName.setDeptName(this.getDeptName());
		userName.setUserName(this.getUserName());
	}

}
