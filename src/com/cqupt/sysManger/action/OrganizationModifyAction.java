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

public class OrganizationModifyAction extends ActionSupport{

	/**
		 * 
		 */
		private static final long serialVersionUID = 1794195924380050122L;
		HttpServletRequest request=null;
Logger logger = Logger.getLogger(getClass());
	
		public String execute()
		{
			this.request = ServletActionContext.getRequest();
			String deptId = request.getParameter("deptId");
			
			if(deptId.equals("undefined")) {
				
				deptId = "1";
			} 
			System.out.println("deptId:"+request.getParameter("deptId"));
			DataStormSession session = null;
			try 
			{
				session = DataStormSession.getInstance();
				/*String sql=	"select a.dept_id,a.dept_name,ifnull(a.dept_address,'') dept_address,ifnull(a.phone_num,'') phone,b.dept_name parent_dept_name," +
						" b.dept_id parent_dept_id,a.dept_state,ifnull(a.remark,'') remark,ifnull(a.company_name,'')  company_name,a.transfer_user_name,a.post_num,a.transfer_user_phone " +
						" from sys_dept a,sys_dept b WHERE a.parent_dept_id = b.dept_id AND a.dept_id  = '"+deptId+"'";				

*/
				String sql = "select * from cqmass.sys_dept a where a.dept_id = '"+deptId+"'";
				logger.info("修改部门时的查询："+sql);
				List resultList = session.findSql(sql);
				Map resultMap = (Map)resultList.get(0);
				request.setAttribute("deptName", resultMap.get("deptName"));
				request.setAttribute("deptId", resultMap.get("deptId"));
//System.out.println("cf12312321="+deptId);				
				request.setAttribute("deptAddr", resultMap.get("deptAddress"));
				request.setAttribute("phone", resultMap.get("phone"));
				request.setAttribute("companyName", resultMap.get("companyName"));
				request.setAttribute("transferUserName", resultMap.get("transferUserName"));
				request.setAttribute("postNum", resultMap.get("postNum"));
				request.setAttribute("parentDeptName", resultMap.get("parentDeptName"));
				request.setAttribute("parentDeptId", resultMap.get("parentDeptId"));
				request.setAttribute("remark", resultMap.get("remark"));
				request.setAttribute("state", resultMap.get("state"));
				request.setAttribute("deptType", resultMap.get("deptType"));
				//request.setAttribute("totalCredit", resultMap.get("totalCredit"));
				//request.setAttribute("availableCredit", resultMap.get("availableCredit"));
				request.setAttribute("transferUserPhone", resultMap.get("transferUserPhone"));
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
			            
       
	        
	       return "modify";


		}
		
		
}
