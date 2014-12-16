package com.cqupt.login;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.Md;
import com.opensymphony.xwork2.ActionSupport;

public class UserMenuAction extends ActionSupport{
	Logger logger = Logger.getLogger(getClass());
	HttpServletRequest request=null;
	public String execute()
	{
		String result="";
		this.request = ServletActionContext.getRequest();
		try {    
			String userId = request.getSession().getAttribute("userId").toString();
			HttpServletResponse response=ServletActionContext.getResponse();
	           //设置字符集    
	           response.setCharacterEncoding("UTF-8");    
	           PrintWriter out = response.getWriter();    
	           //直接输入响应的内容    
	           out.println(getMenu(userId));    
	           out.flush();    
	           out.close();    

	       } catch (Exception e) {    
	           // TODO: handle exception    
	           e.printStackTrace();    
	       }    
	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

	   }  
	
	
	private String getMenu(String userId){
		StringBuilder resultStr = new StringBuilder();
		
		DataStormSession session = null;

		try 
		{
			session = DataStormSession.getInstance();
			String sql="SELECT a.menuid,a.menuname,a.icon,a.url FROM bankaccount.sys_menu a,bankaccount.sys_user_group_oper_auth b,bankaccount.sys_user c WHERE c.user_id = '"+userId+"' and c.group_id=b.group_id  AND a.menuid = b.menuid AND a.menulevel = '1' order by a.menu_order";//选出一级菜单
			logger.info("查询用户操作权限内的菜单:"+sql);			
			List resultList = session.findSql(sql);
			if((resultList.size())==0)
			{
				//result = "登录名"+userId+"不存在";
			} else {
				for(int i=0;i<resultList.size();i++) {
					Map resultMap = (Map)resultList.get(i);
					if( 0==i ){
						resultStr.append("{\"menus\":[");
					}
					resultStr.append("{\"menuid\":\""+resultMap.get("menuid")+"\",");
					resultStr.append("\"icon\":\""+resultMap.get("icon")+"\",");
					resultStr.append("\"menuname\":\""+resultMap.get("menuname")+"\",");
					resultStr.append("\"menus\":");
					String subMenuSql = "SELECT a.menuid,a.menuname,a.icon,a.url FROM bankaccount.sys_menu a,bankaccount.sys_user_group_oper_auth b,bankaccount.sys_user c WHERE c.user_id = '"+userId+"' and c.group_id=b.group_id  AND a.menuid = b.menuid and  a.menuid LIKE '"+resultMap.get("menuid")+"%' AND a.menulevel='2' order by a.menu_order";
					List resultSubMenuList = session.findSql(subMenuSql);
					resultStr.append(JSONArray.fromObject(resultSubMenuList));
					if (i!= (resultList.size()-1)) {
						resultStr.append("},");
					}
					
					
					if (i== (resultList.size()-1)) {
						resultStr.append("}]}");
					}
					
				}
			} 
			session.closeSession();
		}		
		catch (Exception e) 
		{
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		logger.info(resultStr.toString());
		return resultStr.toString();
	}}

