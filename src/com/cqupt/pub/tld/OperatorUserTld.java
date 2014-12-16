package com.cqupt.pub.tld;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;
import com.opensymphony.xwork2.util.ValueStack;
public class OperatorUserTld extends ComponentTagSupport{
	private String deptId;
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		// TODO Auto-generated method stub
		this.setDeptId(arg1.getSession().getAttribute("deptId").toString());
		return new OperatorUser(arg0);
	}
	
	protected void populateParams(){
		super.populateParams();
		OperatorUser OperatorUser = (OperatorUser)component;
		OperatorUser.setDeptId(this.getDeptId());
	}



}
