package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.JsonUtil;
import com.opensymphony.xwork2.ActionSupport;

public class OrganizationListDeleteAction extends ActionSupport{
      Logger logger = Logger.getLogger(getClass());
	/**
		 * 
		 */
		private static final long serialVersionUID = 1794195924380050122L;
		HttpServletRequest request=null;

		private InputStream inputStream;
		public InputStream getInputStream() {
			return inputStream;
		}

		
		public String execute()
		{
			this.request = ServletActionContext.getRequest();
logger.info("OrganizationListDeleteAction:");
			String deptId = request.getParameter("deptId");
logger.info("delete"+deptId);
			HttpServletResponse response=ServletActionContext.getResponse();
	       
	           response.setCharacterEncoding("UTF-8");    
	           PrintWriter out;
			try {
				out = response.getWriter();
				out.print(getDeptList(deptId));    
		        out.flush();    
		        out.close();  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    

	           
	            

	        
	       return null;


		}
		
		
		private String getDeptList(String deptId) {
			DataStormSession session = null;
			String resultStr = "";
			String sql = "";
			try 
			{
				session = DataStormSession.getInstance();
				sql = "delete from  cqmass.sys_dept   WHERE dept_id = '"+deptId+"'";				
logger.info(sql);
                session.delete(sql);
                sql = "delete from  cqmass.credit   WHERE dept_id  = '"+deptId+"'";				
logger.info(sql);
				session.delete(sql);
                sql = "delete from  cqmass.sys_user   WHERE dept_id  = '"+deptId+"'";				
logger.info(sql);
				session.delete(sql);
				session.closeSession();
				resultStr = "success";
			}		
			catch (Exception e) 
			{
				resultStr = "OrganizationListDeleteActionError";
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
