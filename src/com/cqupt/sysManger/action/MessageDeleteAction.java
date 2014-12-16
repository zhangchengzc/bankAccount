package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class MessageDeleteAction extends ActionSupport {
	Logger logger =  Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;

	public String execute()
	{		
		HttpServletRequest request=null;
	    String deptId = null;
logger.info("MessageDeleteAction");
		request = ServletActionContext.getRequest();
		try {
			String messageId = java.net.URLDecoder.decode(request.getParameter("messageId"), "UTF-8");//品牌id
			String userName = request.getSession().getAttribute("userName").toString();
			String userId = request.getSession().getAttribute("userId").toString();
			HttpServletResponse response=ServletActionContext.getResponse();
	           //设置字符集    
	        response.setCharacterEncoding("UTF-8");    
	        PrintWriter out;
	        out = response.getWriter();
			out.print(delteBrand(messageId,userId,userName));    
	        out.flush();    
	        out.close();  
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
		} 
           //直接输入响应的内容    	        
       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    


	}
	
	
	private String delteBrand(String messageId,String userId,String userName) {
		DataStormSession session = null;
		String resultStr = "";
		String sql= "";
		List resultList = null;
		Map resultMapCode = null;
		Map resultMapGadgets = null;
		String id = "";
		String innerTransferId,caseStatus,gadgetsPrameryKey;
		try {
			session = DataStormSession.getInstance();
					
					sql = "update cqmass.message_detail set message_status = '2' ,message_delete_user = '"+userName+"',message_delete_time = sysdate() where message_id  ='"+messageId+"'";
					logger.info("把公告详情表的公告状态改为2："+sql);
					session.update(sql);
					resultStr = "success";
				
			
			
			session.closeSession();
		}catch (Exception e) {
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
