package com.cqupt.login;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.Md;
import com.cqupt.pub.util.ObjectChanger;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class CheckLoginAction extends ActionSupport{
Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;
	private ObjectChanger objChanger = ObjectChanger.getInstance();

	private String userId;
	private String password;
	private String usercaptcha;
	private String userName;
	private String userDept;
	private String userDeptId;
	private String groupId;
	private String dataAuthId;
	
	

	public String getDataAuthId() {
		return dataAuthId;
	}

	public void setDataAuthId(String dataAuthId) {
		this.dataAuthId = dataAuthId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}
	private Md md5fun = new Md();
	
	public String getUsercaptcha() {
		return usercaptcha;
	}

	public void setUsercaptcha(String usercaptcha) {
		this.usercaptcha = usercaptcha;
	}
	
	public String getUserId() 
	{
		return userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	public void setUserDeptId(String userDeptId) {
		this.userDeptId = userDeptId;
	}

	public String getUserDeptId() {
		return userDeptId;
	}
	public String execute()
	{
		logger.info("CheckLoginAction：");
		this.request = ServletActionContext.getRequest();
		ServletContext sc = ServletActionContext.getServletContext();
		String result="success";
		String userId=this.getUserId();
		String userPwd=this.getPassword();
		this.setUserId(request.getAttribute("userId").toString());
		this.setPassword(request.getAttribute("password").toString());
		this.setUsercaptcha(request.getAttribute("usercaptcha").toString());
	
		HttpServletResponse response=ServletActionContext.getResponse();
		//检测该客户端是否有用户已登录
		/*	HttpSession session1 = request.getSession();         //把用户放进session	
		String old_user_name = (String)session1.getAttribute("userId");
		logger.info("************"+session1.getId()+session1.getAttribute("userId")+"************");
//	System.out.println("old_user_name:"+old_user_name);
//	System.out.println(old_user_name!=null);

		
		
		
		if( old_user_name!=null ){
			//强制用户下线
			//OnlineUser.online.get(old_user_name).invalidate();		
			
			result = result+"@isExist";
		}else{
			result = checkPwd(userId, userPwd); //验证登录名		
			if(result.equals("success"))         //登录名成功验证,验证验证码
				result = checkCaptcha();			
			if(result.equals("success"))        //验证码成功验证是否登陆
				result = checkLogin(userId,request.getRemoteAddr(),sc);
			result = result+"@noExist";	
		}
		
*/
		result = checkPwd(userId, userPwd); //验证登录名		
		if(result.equals("success"))         //登录名成功验证,验证验证码
			result = checkCaptcha();			
		if(result.equals("success"))        //验证码成功验证是否登陆
			result = checkLogin(userId,request.getRemoteAddr(),sc);
		 response.setCharacterEncoding("UTF-8");    
         PrintWriter out;
		try {
			out = response.getWriter();
			out.print(result);    
	        out.flush();    
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}    

         //直接输入响应的内容    
             
		return null;
	}
	
	 /**
     * 用户密码验证
     * @param prop
     * @return useIdError-登录名错误 pwdError-密码错误  success-验证成功 ---sysError-系统异常
     * @throws 
     */
	String checkPwd(String userId, String userPwd) {
		String result;
		DataStormSession session = null;
		try 
		{
			session = DataStormSession.getInstance();
			String sql="select user_pwd,dept_name,t.user_name,dept_id,group_id from sys_user t where t.user_id='"+userId+"' and t.user_state = '可用' ";//在用户表中根据userId来获取密码的MD5值
			logger.info("检测登录用户信息sql："+sql);
			List resultList = session.findSql(sql);
			if((resultList.size())==0)
			{
				result = "登录名"+userId+"不存在或已禁用";
			} else{
				Map resultMap = (Map)resultList.get(0);		
				String passwd = resultMap.get("userPwd").toString();
				setUserName(resultMap.get("userName").toString());
				setUserDept(resultMap.get("deptName").toString());
				setUserDeptId(resultMap.get("deptId").toString());
				setGroupId(resultMap.get("groupId").toString());
				/*
				sql = "SELECT DISTINCT b.data_auth_id FROM  sys_user a,sys_user_group_data_auth b WHERE a.user_id ='"+userId+"' AND b.group_id =a.group_id";
System.out.println("sql"+sql);
				List resultList1 = session.findSql(sql);
				StringBuffer sb = new StringBuffer();
				
				
				for(int i = 0; i < resultList1.size(); i++) {
					Map resultMap1 = (Map)resultList1.get(i);
					sb.append("'"+resultMap1.get("dataAuthId")+"'");
					if(i != resultList1.size()-1)
						sb.append(",");
				}

*/				
				//根据部门、角色组、用户　来确定该用户的数据权限(userId取传进来的值）
				StringBuffer sb = new StringBuffer();
				
				String deptId = resultMap.get("deptId").toString();
				String groupId = resultMap.get("groupId").toString();
				List dataAuthList = null;
				Map dataAuthMap = null;
				String dataType = "";
				//判断部门
				if(deptId.length() == 1){					
					//部门号长度为１的是总库房和测试管理部，要能看到所有网点的数据
					logger.info("!!!!!!本用户为管理部用户!!!!!!");
					//总库房下要分角色取数据权限
					sql = "select t.data_type from bankaccount.sys_data_auth t where t.dept_type='管理部' and t.group_id = '"+groupId+"'";
					if(groupId.equals("119")){
						//角色为服务支撑
						sql += " and t.user_id = '"+userId+"'";						
					}
					logger.info("判断登录用户的数据权限sql："+sql);
					dataAuthList = session.findSql(sql);
					dataAuthMap = (Map)dataAuthList.get(0);
					
					dataType = dataAuthMap.get("dataType").toString();
					if(dataType.equals("allDeptId")){
						//读取所有部门ＩＤ号
						sql = "select t.dept_id from bankaccount.sys_dept t where t.dept_state = '可用'";
						logger.info("读取所有部门ＩＤ号sql："+sql);
						dataAuthList = session.findSql(sql);
						for(int i = 0; i < dataAuthList.size(); i++) {
							Map resultMap1 = (Map)dataAuthList.get(i);
							sb.append("'"+resultMap1.get("deptId")+"'");
							if(i != dataAuthList.size()-1)
								sb.append(",");
						}
						//数据权限如:'1','101','102'
					}else{
						//把dataType分离出来,并计算出相应部门的下级部门ID
						String[] dataAuthArray = dataType.split(";");
						for(int i = 0; i < dataAuthArray.length; i++) {
							sql = "select t.dept_id from bankaccount.sys_dept t where t.dept_id like '"+dataAuthArray[i]+"%' and t.dept_state = '可用'";
							logger.info("读取相应部门的子部门ＩＤ号sql："+sql);							
							dataAuthList = session.findSql(sql);
							if(i != 0 ){
								sb.append(",");
							}
							for(int j = 0; j < dataAuthList.size(); j++) {
								Map resultMap1 = (Map)dataAuthList.get(j);
								sb.append("'"+resultMap1.get("deptId")+"'");
								if(j != dataAuthList.size()-1)
									sb.append(",");
							}//for j
							
						}//for i
						
					}
					
				}else{
					logger.info("!!!!!!本用户为网点用户!!!!!!");
					//各维修网点和接机点 ,只能看到自己网点的数据
					sql = "select t.data_type from sys_data_auth t where t.dept_type='网点'";
					logger.info("判断登录用户的数据权限sql："+sql);
					dataAuthList = session.findSql(sql);
					dataAuthMap = (Map)dataAuthList.get(0);
					dataType = dataAuthMap.get("dataType").toString();
					if(dataType.equals("selfDeptId")){
						sb.append(deptId);//数据权限为本部门
					}
					
				}
				
				logger.info("最后的数据权限dataAuthId:"+sb.toString());
	            setDataAuthId(sb.toString());
	           
				if(passwd.equals(md5fun.getMD5ofStr(userPwd))) {//md5加密
					result = "success";
				}else {
					result = "登录名"+userId+"密码输入错误";
				}		
			} 
			session.closeSession();
		}		
		catch (Exception e) 
		{
			result = "系统异常，请联系系统管理员";
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result;
		
	}
	/*
	 * 验证码是否正确
	 */
	String checkCaptcha(){
		HttpSession session = request.getSession();
		String result = "success";

		String captcha = session.getAttribute("randomStr").toString();
		if(!captcha.equals(this.getUsercaptcha())) {
			result = "验证码输入错误，请重新输入";
		}
			
		return result;
	}
	/*
	 * 验证账号是否已经登陆
	 */
	String checkLogin (String userId,String addr,ServletContext sc){
		String result="success";
		String loginTime = "";
		try {
			loginTime = objChanger.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
		} catch (CquptException e) {
			e.printStackTrace();
			//系统错误
			result = "系统异常，请联系系统管理员";
		}
try {
	
	//取消单个用户登录机制
	HttpSession session = request.getSession();         //把用户放进session	
	
	

	
	session.setAttribute("userId", userId);//
	session.setAttribute("userIp", addr);//
	session.setAttribute("loginTime", loginTime);//
	session.setAttribute("userName", getUserName());
	session.setAttribute("deptName", getUserDept());
	session.setAttribute("deptId", getUserDeptId());
	session.setAttribute("groupId", getGroupId());
	session.setAttribute("dataAuthId", getDataAuthId());

	
	OnlineUser.online.put(userId,session);
	
	/*	限制用户ID同时不能多个地方登录
	 * if(OnlineUser.online.containsKey(userId) == false){	//此员工未曾登陆			
			HttpSession session = request.getSession();         //把用户放进session	
			session.setAttribute("userId", userId);//
			session.setAttribute("userIp", addr);//
			session.setAttribute("loginTime", loginTime);//
			session.setAttribute("userName", getUserName());
			session.setAttribute("userDept", getUserDept());
			session.setAttribute("deptId", getUserDeptId());
			session.setAttribute("rlId", getRlId());
			session.setAttribute("dataAuthId", getDataAuthId());
			System.out.println("this.getRlId()"+this.getDataAuthId());
			
			OnlineUser.online.put(userId,session);
		
		} else if( ((HttpSession)OnlineUser.online.get(userId)).getAttribute("userIp").equals(addr) )
		{
			((HttpSession)OnlineUser.online.get(userId)).invalidate();
			//清空上次登陆的session
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);//
			session.setAttribute("userIp", addr);//
			session.setAttribute("loginTime", loginTime);//
			session.setAttribute("userName", getUserName());
			session.setAttribute("userDept", getUserDept());
			session.setAttribute("deptId", getUserDeptId());
			session.setAttribute("rlId", getRlId());
			OnlineUser.online.put(userId,session);
			result = "success";
		} else {
			result = "登录名"+userId+"已经登陆，登陆ip地址为 " + ((HttpSession)OnlineUser.online.get(userId)).getAttribute("userIp");
		}*/
		}
		catch (Exception e) {
			e.printStackTrace();
			//系统错误
			result = "系统异常，请联系系统管理员";
		}
		return result;
	}

	


}
