package com.cqupt.login;


import java.util.Date;
import java.util.List;
import java.util.Map;
import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.Md;
import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.cqupt.pub.util.ObjectCensor;
import com.cqupt.pub.util.ObjectChanger;



/**
 * 用户登录验证  
 * @author sulei
 * 
 *
 */
public class LoginAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;
	
	public String execute()
	{	
		return "success";
	}
	
}
