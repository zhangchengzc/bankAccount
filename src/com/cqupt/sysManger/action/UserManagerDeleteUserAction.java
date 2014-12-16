package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class UserManagerDeleteUserAction extends ActionSupport{

	private static final long serialVersionUID = -3592801814695320357L;
	


	HttpServletRequest request = null;

	public String execute() {
		request = ServletActionContext.getRequest();
		String userId = request.getParameter("userId");
		HttpServletResponse response = ServletActionContext.getResponse();
	
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(deleteSupplierRecord(userId));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private String deleteSupplierRecord(String userId) {
		
		DataStormSession session = null;
		String resultStr = "";
		String sql = "";
		try {
			session = DataStormSession.getInstance();
			
			sql = "delete from sys_user  WHERE user_id = '"+userId+"'";
			session.delete(sql);
			sql="delete from money_save where card_number='"+userId+"'";
			session.delete(sql);
			sql="delete from money_draw where card_number='"+userId+"'";
			session.delete(sql);
			sql="delete from money_savedetail where card_number='"+userId+"'";
			session.delete(sql);
			sql="delete from money_drawdetail where card_number='"+userId+"'";
			session.delete(sql);
			session.closeSession();
			resultStr = "success";
		} catch (Exception e) {
			resultStr = "error";
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} 
		return resultStr;
	}

}
