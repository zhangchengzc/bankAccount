package com.cqupt.pub.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.JsonUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class CheckServiceQueryAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	Logger logger =Logger.getLogger(this.getClass());
	HttpServletRequest request=null;	
	
	
	public String execute(){
		request = ServletActionContext.getRequest();			
		String userName = request.getSession().getAttribute("userName").toString();
		String deptId = request.getSession().getAttribute("deptId").toString();
		String type = request.getParameter("type");
		if( type == null){
			type ="";
		}else if(type.equals("check")){
			type = "where (case_next_status = '检测') and is_transfer = '' ";
		}else if(type.equals("repair")){
			type = "where (case_next_status = '维修') and is_transfer = '' ";
		
		}else if(type.equals("back2User")){
			type = " where case_next_status = '返回客户' and is_transfer = '' ";
		}
		
		HttpServletResponse response=ServletActionContext.getResponse();
        //设置字符集    
		response.setCharacterEncoding("UTF-8");    
		PrintWriter out;
		try {
			out = response.getWriter();
			   //直接输入响应的内容    
	        out.println(getList(type,userName,deptId));    
	        out.flush();    
	        out.close();    
		} catch (IOException e) {
			e.printStackTrace();
		}    
		return null;
	}
	
	public String getList(String type,String userName,String deptId){
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;
		try{
			session = DataStormSession.getInstance();
			sql = "select @rownum:=@rownum+1 AS rownum, t.case_num,brand_name," +
					"date_format(in_date,'%Y-%c-%d %h:%i:%s') case_accept_time,appoint_user,oper_user_name,t.case_type,version_name,appoint_user, m.case_status,	m.case_next_status "
					+" from (SELECT @rownum:=0) r , cqmass.case_accept t,cqmass.case_status m where t.case_num in (select case_num from cqmass.case_status  "+type ;	
			
			sql = sql +" AND m.case_num = t.case_num )  and oper_user_name='"+userName+"'  ORDER BY case_num DESC";
			logger.info("欢迎页面查询待检测工单sql:"+sql);
			resultStr = JsonUtil.list2json(session.findSql(sql));
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
		logger.debug("resultStr"+resultStr);
		return resultStr;
	}
}
