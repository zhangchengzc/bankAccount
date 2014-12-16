package com.cqupt.sysManger.tld;

import java.io.IOException;
import java.io.Writer;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

public class UserName extends Component{
	private String deptName;
	private String userName;
	
	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public UserName(ValueStack stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}
	
	public boolean start(Writer writer){
		boolean result = super.start(writer);
		try{
			String str = getUserName();
			writer.write(str);
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	private String getUserName(){
		StringBuilder resultStr = new StringBuilder();
		resultStr.append("  ");
		resultStr.append(" 部门:"+this.deptName);
		resultStr.append(" 用户:"+this.userName+"  ");	
		return resultStr.toString();
	}

}
