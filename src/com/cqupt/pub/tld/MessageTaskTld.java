
package com.cqupt.pub.tld;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class MessageTaskTld extends ComponentTagSupport{
	
	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		// TODO Auto-generated method stub
		return new MessageTask(arg0);
	}
	
	protected void populateParams(){
		super.populateParams();
		MessageTask MessageTask = (MessageTask)component;
	}

}
