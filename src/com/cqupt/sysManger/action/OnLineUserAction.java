package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cqupt.login.OnlineUser;
import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class OnLineUserAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;
	HttpSession _session=null;
	
	
	
	public String execute()
	{
		request=ServletActionContext.getRequest();
		String pagesize = request.getParameter("pagesize");
		String page = request.getParameter("page");
		

			//查看request中所有的参数
			Enumeration enu=request.getParameterNames(); 
System.out.print("取request中所有的参数");
			while(enu.hasMoreElements()){ 
			String paraName=(String)enu.nextElement(); 
System.out.println(paraName+": "+request.getParameter(paraName)); 
			} 

			HttpServletResponse response=ServletActionContext.getResponse();
	           //设置字符集    
	           response.setCharacterEncoding("UTF-8");    
	           PrintWriter out;
			try {
				out = response.getWriter();
				   //直接输入响应的内容    
		        out.println(getOnLineUserData(page,pagesize));    
		        out.flush();    
		        out.close();    
			} catch (IOException e) {
				e.printStackTrace();
			}    

	        

	   
	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

	   }  
	
	private String getOnLineUserData(String page,String pagesize){
		//Iterator it = OnlineUser.online.keySet().iterator();
		int onlineUserCount = 0;

		StringBuilder resultStr = new StringBuilder();
		resultStr.append("{Rows:[");		

		
		Set<String> key = OnlineUser.online.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
            
            //System.out.println(map.get(s));
            onlineUserCount++;
            String userID = (String) it.next();
System.out.println("在线用户数："+onlineUserCount);
System.out.println((Integer.parseInt(page)-1)*Integer.parseInt(pagesize)+1);
System.out.println(Integer.parseInt(page)*Integer.parseInt(pagesize));
			if((onlineUserCount >= (Integer.parseInt(page)-1)*Integer.parseInt(pagesize)+1)&&(onlineUserCount <= Integer.parseInt(page)*Integer.parseInt(pagesize)))
			{
				
				resultStr.append("{\"userID\":"+"\""+userID+"\",");
				resultStr.append("\"userIp\":"+"\""+(((HttpSession)OnlineUser.online.get(userID))).getAttribute("userIp").toString()+"\",");
				resultStr.append("\"loginTime\":"+"\""+(((HttpSession)OnlineUser.online.get(userID))).getAttribute("loginTime").toString()+"\",");
				resultStr.append("\"userName\":"+"\""+(((HttpSession)OnlineUser.online.get(userID))).getAttribute("userName").toString()+"\",");
				if(onlineUserCount<OnlineUser.online.size()) {
					resultStr.append("\"userDept\":"+"\""+(((HttpSession)OnlineUser.online.get(userID))).getAttribute("deptName").toString()+"\"},");
				} else {
					resultStr.append("\"userDept\":"+"\""+(((HttpSession)OnlineUser.online.get(userID))).getAttribute("deptName").toString()+"\"}");
				}

			}
        }


		resultStr.append("],Total:"+onlineUserCount+"}");	
System.out.println(resultStr.toString());
		return resultStr.toString();
	}
	}
