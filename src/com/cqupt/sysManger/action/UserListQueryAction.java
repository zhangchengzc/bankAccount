package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.JsonUtil;
import com.opensymphony.xwork2.ActionSupport;

public class UserListQueryAction  extends ActionSupport{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2465087541585226388L;
	/**
	 *   查询用户信息
	 */

	HttpServletRequest request = null;

	public String execute() {
		request = ServletActionContext.getRequest();
		String txtName = request.getParameter("txtName");
		String txtUserId = request.getParameter("txtUserId");
		String parentDeptName = request.getParameter("parentDeptName");
		String hidParentDeptId = request.getParameter("hidParentDeptId");
	
		try {
			if(txtName != null)
				txtName = java.net.URLDecoder.decode(txtName, "utf-8");
			else 
				txtName = "";
			if(txtUserId != null)
				txtUserId = java.net.URLDecoder.decode(txtUserId, "utf-8");
			else 
				txtUserId = "";
			if(parentDeptName != null)
				parentDeptName = java.net.URLDecoder.decode(parentDeptName, "utf-8");
			else 
				parentDeptName = "";
			if(hidParentDeptId != null)
				hidParentDeptId = java.net.URLDecoder.decode(hidParentDeptId, "utf-8");
			else 
				hidParentDeptId = "";
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		if(txtName.equals("全部")) {
			txtName = "";
		} 
		if(txtUserId.equals("全部")) {
			txtUserId = "";
		} 
		if(parentDeptName.equals("全部")) {
			parentDeptName = "";
		} 
		if(hidParentDeptId.equals("全部")) {
			hidParentDeptId = "";
		} 
	
	
		System.out.println("用户姓名：" + txtName);
		System.out.println("用户登录名：" + txtUserId);
		System.out.println("用户部门名称：" + parentDeptName);
		System.out.println("用户部门ID：" + hidParentDeptId);

		String pageSize = request.getParameter("pagesize");
		String page = request.getParameter("page");
		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置字符集
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(getUserList(txtName, txtUserId, parentDeptName, hidParentDeptId, pageSize, page));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 直接输入响应的内容
		return null;// 不需要跳转某个视图 因为上面已经有了直接输出的响应结果
	}

	private char[] getUserList(String txtName, String txtUserId, String parentDeptName, String hidParentDeptId, String pageSize, String page) {
		
		DataStormSession session = null;
		String resultStr = "";
		String sql = "";
		try {	
				sql = "SELECT b.*,ROWNUM order_id FROM (select @rownum:=@rownum+1 as rownum, t.user_id,t.user_name,t.group_name,ifnull(t.user_email,' ') user_email,t.dept_name,ifnull(t.phone_num,' ') user_mob_phone,t.user_state  "+
			 	  " from (select @rownum:=0) r, sys_user t  "+
			 	  "WHERE t.user_name LIKE '%"+txtName+"%' ";
			 	  if(!txtUserId.equals("")) {
			 		  sql += " AND t.user_id = '"+txtUserId+"'";
			 	  }
			 	 if(!hidParentDeptId.equals("")) {
			 		  sql += " AND t.dept_id = '"+hidParentDeptId+"'";
			 	  }
			 	 sql += " ) b";
			 	// sql += " AND a.group_id = t.group_id ) b";,sys_user_group a

System.out.println(sql);	
			session = DataStormSession.getInstance();
			Map resultMap = session.findSql(sql, Integer.parseInt(page), Integer.parseInt(pageSize));
			resultStr = JsonUtil.map2json(resultMap);
System.out.println("resultStr:"+resultStr);
		} catch (CquptException ce) {
			ce.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.closeSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(resultStr);
		return resultStr.toCharArray();
	}

}
