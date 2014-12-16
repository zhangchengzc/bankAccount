package com.cqupt.sysManger.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;
import common.Logger;

public class MessageReadAction extends ActionSupport{

	private static final long serialVersionUID = -1015300446840329702L;
	Logger logger =  Logger.getLogger(this.getClass());
	HttpServletRequest request = null;

	public String execute() throws Exception {
logger.info("MessageReadAction:");
		request = ServletActionContext.getRequest();
		String deptId = request.getSession().getAttribute("deptId").toString();
		String userName = request.getSession().getAttribute("userName").toString();
		String messageId =  request.getParameter("messageId");
		DataStormSession session = null;
		try 
		{
			session = DataStormSession.getInstance();
		
			String sql = "select @rownum:=@rownum+1 AS rownum, a.message_id,a.message_type,a.message_title,date_format(a.send_time,'%Y-%c-%d %H:%i:%s') send_time,a.message_content,a.appendix_name,a.send_user" +
					"  from (SELECT @rownum:=0) r ,cqmass.message_detail a where message_status = '1' and message_id = '"+messageId+"'";;
logger.info("查询公告详细信息"+sql);			
			List resultList = session.findSql(sql);
			Map resultMap = (Map)resultList.get(0);
			request.setAttribute("messageId", resultMap.get("messageId").toString());
			request.setAttribute("messageType", resultMap.get("messageType").toString());
			request.setAttribute("messageTitle", resultMap.get("messageTitle").toString());
			request.setAttribute("messageContent", resultMap.get("messageContent").toString());
			request.setAttribute("sendTime", resultMap.get("sendTime").toString());
			request.setAttribute("sendUser", resultMap.get("sendUser").toString());
			request.setAttribute("appendixName", resultMap.get("appendixName").toString());
		 sql = "update cqmass.message_read_detail a set read_time = sysdate(),read_user = '"+userName+"' ,read_status = '0' where dept_id = '"+deptId+"' and message_id = '"+messageId+"' and read_status = '1'";;
logger.info("更新公告阅读详细信息"+sql);
         session.update(sql);
			session.closeSession();
		}		
		catch (Exception e) 
		{
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		            
		
			return "success";
		}



}
