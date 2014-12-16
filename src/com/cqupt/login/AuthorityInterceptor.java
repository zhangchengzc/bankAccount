package com.cqupt.login;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorityInterceptor extends AbstractInterceptor{
	/**
	 * Session过滤用的拦截器，该拦截器查看用户Session中是否存在特定的属性（LOGIN属性）
	 *如果不存在，中止后续操作定位到LOGIN，否则执行原定操作
	 */
	Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = -4932591586021339992L;

	public String intercept(ActionInvocation ai) throws Exception {
		// 确认Session中是否存在LOGIN
		ActionContext ctx = ai.getInvocationContext();
        Map session = ctx.getSession();
        //String user = (String) session.get("user");
        String actionName = ai.getInvocationContext().getName();
		logger.debug("登陆用户姓名："+session.get("userName"));
		logger.debug("Session中是否存在登录信息："+sessionIsUseable(session));

	 if((sessionIsUseable(session)&& ServletActionContext.getRequest().isRequestedSessionIdValid())||actionName.equals("checkLoginAction")) {
     	// 存在的情况下进行后续操作
     	return ai.invoke();
     } else {
     	// 否则终止后续操作，返回LOGIN
     	return "login";
     }
    }
	
	public boolean sessionIsUseable(Map session) {
		if( session.get("userName") == null)
			return false;
		if( session.get("dataAuthId") == null)
			return false;	
		if( session.get("groupId") == null)
			return false;
		if( session.get("userId") == null)
			return false;
		if( session.get("userIp") == null)
			return false;
		if( session.get("loginTime") == null)
			return false;
		if( session.get("deptName") == null)
			return false;
		
		if( session.get("deptId") == null)
			return false;
	
		

		return true;
	}
}
