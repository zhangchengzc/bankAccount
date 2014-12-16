package com.cqupt.sysManger.action;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.JsonUtil;
import com.cqupt.pub.util.Md;
import com.opensymphony.xwork2.ActionSupport;
public class CardNumberQueryAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	Logger logger = Logger.getLogger(this.getClass());
	private Md md5fun = new Md();
	public String execute() {
		logger.info("MetricsQueryAction:)");
		request = ServletActionContext.getRequest();

		response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			// 直接输入响应的内容
			out.println(getData());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;// 不需要跳转某个视图 因为上面已经有了直接输出的响应结果

	}

	private String getData() {
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;
		String metricsGroup = "";
		HttpSession session1 = request.getSession();
		try {
			metricsGroup = java.net.URLDecoder.decode(request.getParameter("cardNumber"));
			String passWord = java.net.URLDecoder.decode(request.getParameter("passWord"));
			String userId = (String) session1.getAttribute("userId");
			//machineType.equals("PC") ? "1" : "2";
			session = DataStormSession.getInstance();
			sql = "select user_id from sys_user where user_id = '"+metricsGroup+"'";
			if(!passWord.equals(""))
			{
				sql+=" and '"+md5fun.getMD5ofStr(passWord)+"' = (select user_pwd from sys_user where user_id = '"+userId+"')";
			}
			logger.info("监控指标查询："+sql);
			JSONArray jsonObject = JSONArray.fromObject(session.findSql(sql));
			resultStr = jsonObject.toString();
			
			
			session.closeSession();
		} catch (Exception e) {
			try {
				if(null != session) {
					session.exceptionCloseSession();
				}
			} catch (CquptException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} 
		logger.info(resultStr);
		return resultStr;
	}
}


