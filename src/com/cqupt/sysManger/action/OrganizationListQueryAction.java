package com.cqupt.sysManger.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.JsonUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class OrganizationListQueryAction extends ActionSupport{
		Logger logger = Logger.getLogger(getClass());
		private static final long serialVersionUID = 1794195924380050122L;
		HttpServletRequest request=null;

		
		
		private InputStream inputStream;
		public InputStream getInputStream() {
			return inputStream;
		}

		
		public String execute()
		{
//System.out.println("1```````````````````");	
			logger.info("OrganizationListQueryAction:");
			this.request = ServletActionContext.getRequest();
			String deptId = request.getParameter("deptId");
//System.out.println("2```````````````````");		
			if(deptId.equals("undefined")) {
				//若为初始化，则根据权限判断deptId
				
				deptId = "";

			} 
			

            String pageSize = request.getParameter("pagesize");
            String page = request.getParameter("page");
			HttpServletResponse response=ServletActionContext.getResponse();
	           //设置字符集    
	           response.setCharacterEncoding("UTF-8");    
	           PrintWriter out;
			try {
				out = response.getWriter();
				out.println(getDeptList(deptId,pageSize,page));    
		        out.flush();    
		        out.close();  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    

	           //直接输入响应的内容    
	            

	        
	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    


		}
		
		
		private String getDeptList(String deptId,String pageSize,String page) {
			DataStormSession session = null;
			String resultStr = "";
			
			try 
			{   
				session = DataStormSession.getInstance();
				String sql="";
				/*if(deptId.length()==1){
					sql="SELECT @rownum:=@rownum+1 as rownum,  m.*,c.total_credit,c.available_credit,ifnull(c.contract_credit,0) contract_credit " +
							" from (select @rownum:=0) r," +
							"(select  s.dept_id,s.dept_name,s.company_name,s.transfer_user_name,s.post_num,s.transfer_user_phone," +
							"t.dept_name parent_dept_name,ifnull(s.dept_address,' ') dept_address," +
							"ifnull(s.phone_num,' ') phone_num,ifnull(s.email,' ') email,s.dept_state," +
							"ifnull(s.remark ,' ') remark from  cqmass.sys_dept s " +
							"join cqmass.sys_dept t where s.parent_dept_id=t.dept_id " +
							" and s.dept_id like '"+deptId+"%' and s.dept_state = '可用' ORDER BY s.dept_id) m " +
							" join cqmass.credit c on m.dept_id = c.dept_id";
				}else {
					sql="SELECT @rownum:=@rownum+1 as rownum,  m.*,c.total_credit,c.available_credit,ifnull(c.contract_credit,0) contract_credit " +
					" from (select @rownum:=0) r," +
					"(select  s.dept_id,s.dept_name,s.company_name,s.transfer_user_name,s.post_num,s.transfer_user_phone," +
					"t.dept_name parent_dept_name,ifnull(s.dept_address,' ') dept_address," +
					"ifnull(s.phone_num,' ') phone_num,ifnull(s.email,' ') email,s.dept_state," +
					"ifnull(s.remark ,' ') remark from  cqmass.sys_dept s " +
					"join cqmass.sys_dept t where s.parent_dept_id=t.dept_id " +
					"and s.dept_id = '"+deptId+"'  and s.dept_state = '可用' ORDER BY s.dept_id) m " +
					"join cqmass.credit c on m.dept_id = c.dept_id";
				}	*/	
				sql="SELECT @rownum:=@rownum+1 as rownum,  m.*,c.total_credit,c.available_credit,ifnull(c.contract_credit,0) contract_credit " +
				" from (select @rownum:=0) r," +
				"(select  s.dept_id,s.dept_name,s.company_name,s.transfer_user_name,s.post_num,s.transfer_user_phone,s.sale_centre," +
				"t.dept_name parent_dept_name,ifnull(s.dept_address,' ') dept_address," +
				"ifnull(s.phone_num,' ') phone_num,ifnull(s.email,' ') email,s.dept_state," +
				"ifnull(s.remark ,' ') remark,date_format(s.in_date, '%Y-%c-%d %H:%i:%s') in_date from  cqmass.sys_dept s " +
				"join cqmass.sys_dept t where s.parent_dept_id=t.dept_id " +
				" and s.dept_state = '可用' " ;
				if(!deptId.equals("")){
					if(deptId.equals("2")){
						sql += " and s.parent_dept_id like '"+deptId+"%'";
					}else{
						sql += " and s.dept_id like '"+deptId+"%'";
					}
				}
				sql += " ORDER BY s.dept_id ) m left join cqmass.credit c on m.dept_id = c.dept_id" ;
				
logger.info("查询部门："+sql);
				Map resultMap = session.findSql(sql, Integer.parseInt(page), Integer.parseInt(pageSize));

				resultStr = new JsonUtil().map2json(resultMap);	
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
			return resultStr;

		}
		
}
