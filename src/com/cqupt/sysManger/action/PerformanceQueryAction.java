package com.cqupt.sysManger.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.Md;
import com.opensymphony.xwork2.ActionSupport;

public class PerformanceQueryAction extends ActionSupport{
	Logger logger =  Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;	
	HttpServletResponse response = null;
	
	public String execute(){
		request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		logger.info("PerformanceQueryAction:");	
		request = ServletActionContext.getRequest();
		response=ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        String deptId = (String)session.getAttribute("userId");
        getList(deptId);
        return SUCCESS;

	}
	
	public String getList(String deptId){
		String resultStr = "";
		String sql = "";
		int rowCount = 0;
		int tickInterval = 0;
		DataStormSession session = null;
		
		String machineType = java.net.URLDecoder.decode(request.getParameter("machineType"));
		String txtBeginDate = java.net.URLDecoder.decode(request.getParameter("txtBeginDate"));
		String txtEndDate = java.net.URLDecoder.decode(request.getParameter("txtEndDate"));
		try{
			session = DataStormSession.getInstance();	
			
			if(machineType.equals("PC"))
			{
				sql="select * from money_savedetail where card_number= '"+deptId+"'";
				if(txtBeginDate!="")
				{
					sql+=" and date_format(date,'%Y-%m-%d') between '"+txtBeginDate+"' and '"+txtEndDate+"'";
				}
				logger.info("性能资源查询sql:"+ sql);	
				List<Map<String, Object>> rows = session.findSql(sql);
				StringBuilder timestamps = new StringBuilder();
				StringBuilder values = new StringBuilder();
				
				
				for(Map<String, Object> m : rows) {
					timestamps.append("'" + m.get("date") + "',");
					values.append(m.get("saveMoney") + ",");
				}
				//去掉最后一个逗号
				if(0 != values.length()) {
					timestamps.deleteCharAt(timestamps.length() - 1);
					values.deleteCharAt(values.length() - 1);
					rowCount ++;
				}
				
				System.out.println(timestamps.toString());
				System.out.println(values.toString());
				request.setAttribute("timestamps", timestamps.toString());
				request.setAttribute("values", values.toString());
				//设置时间轴间隔
				tickInterval = rowCount / 8 <= 0 ? rowCount / 8 : 1;
				request.setAttribute("tickInterval", "" + tickInterval);
				
				
			}
			else{
				sql="select * from money_drawdetail where card_number= '"+deptId+"'";
				if(txtBeginDate!="")
				{
					sql+=" and date_format(date,'%Y-%m-%d') between '"+txtBeginDate+"' and '"+txtEndDate+"'";
				}
				logger.info("性能资源查询sql:"+ sql);	
				List<Map<String, Object>> rows = session.findSql(sql);
				StringBuilder timestamps = new StringBuilder();
				StringBuilder values = new StringBuilder();
				
				
				for(Map<String, Object> m : rows) {
					timestamps.append("'" + m.get("date") + "',");
					values.append(m.get("drawMoney") + ",");
				}
				//去掉最后一个逗号
				if(0 != values.length()) {
					timestamps.deleteCharAt(timestamps.length() - 1);
					values.deleteCharAt(values.length() - 1);
					rowCount ++;
				}
				
				System.out.println(timestamps.toString());
				System.out.println(values.toString());
				request.setAttribute("timestamps", timestamps.toString());
				request.setAttribute("values", values.toString());
				//设置时间轴间隔
				tickInterval = rowCount / 8 <= 0 ? rowCount / 8 : 1;
				request.setAttribute("tickInterval", "" + tickInterval);
				
			}

			
			

			session.closeSession();
		}catch (CquptException ce) {
			ce.printStackTrace();
			if (session != null) {
				try {
					session.exceptionCloseSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info(resultStr);
		return resultStr;
	}

}
