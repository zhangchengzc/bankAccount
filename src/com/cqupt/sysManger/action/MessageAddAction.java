package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.Md;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class MessageAddAction  extends ActionSupport{

	private static final long serialVersionUID = -6941574699319426537L;

		HttpServletRequest request=null;
		String nowTimeStr = ""; // 保存当前时间
		Random r = new Random(); // 一个随机数对象
		SimpleDateFormat sDateFormat;
         Logger logger = Logger.getLogger(getClass());
		public String execute()
		{
			
			

			this.request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String operUserName = request.getSession().getAttribute("userName").toString();

			try {
				 String deptId = request.getSession().getAttribute("deptId").toString();
				 String deptName = request.getSession().getAttribute("deptName").toString();
					String toDeptName = request.getParameter("toDeptName");		
					String messageTitle = request.getParameter("messageTitle");			
					String messageType = request.getParameter("messageType");			
					String messageContent = request.getParameter("messageContent");			
					String appendixName = request.getParameter("appendixName");			
				int rannum = (int) (r.nextDouble() * (99 - 10 + 1)) + 10; // 获取随机数
				sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
				nowTimeStr = sDateFormat.format(new Date()); // 当前时间
				String messageId = "M"+nowTimeStr + rannum;//inId跟保存的文件名一样	
				try{
					if(toDeptName != null){
						toDeptName = java.net.URLDecoder.decode(toDeptName, "UTF-8");
					}else	toDeptName = "";
					if(messageTitle != null){
						messageTitle = java.net.URLDecoder.decode(messageTitle, "UTF-8");
					}else	messageTitle = "";
					
					if(messageType != null){
						messageType = java.net.URLDecoder.decode(messageType, "UTF-8");
					}else messageType = "";	
					if(messageContent != null){
						messageContent = java.net.URLDecoder.decode(messageContent, "UTF-8");
					}else messageContent = "";
					if(appendixName != null){
						appendixName = java.net.URLDecoder.decode(appendixName, "UTF-8");
					}else appendixName = "";
					
				}catch(UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				HttpServletResponse response=ServletActionContext.getResponse();
		          
		        response.setCharacterEncoding("UTF-8");    
		        PrintWriter out;
		        out = response.getWriter();
				out.print(insertUserinfo( deptId,deptName, toDeptName, messageTitle, messageType, messageContent, appendixName,messageId,operUserName));    
		        out.flush();    
		        out.close();  
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}    
	               
	       return null;


		}
		
		
		private String insertUserinfo(String deptId,String deptName, String toDeptName,String  messageTitle, String messageType,String  messageContent,String  appendixName,String messageId,String operUserName) {
			DataStormSession session = null;
			String resultStr = "";
			String sql= "";
			
			try {
				session = DataStormSession.getInstance();
				logger.info(toDeptName);
				String[] toDeptNameArray = null;
				
				if(toDeptName.equals("所有部门")){
					sql = "select dept_name from sys_dept";
					
					List resultList = session.findSql(sql);
					toDeptNameArray = new String[resultList.size()];
System.out.println(resultList);
					for(int i = 0; i < resultList.size(); i++){
						Map resultMap = (Map)resultList.get(i);
						toDeptNameArray[i] = (String)resultMap.get("deptName");
					}
System.out.println(Arrays.toString(toDeptNameArray));
					
					
					
				} else {
					toDeptNameArray = toDeptName.split(";");
				}
				
				
				
				sql = "INSERT INTO message_detail(primary_key,message_id,message_title,message_type,message_content,appendix_name,send_time,send_user,message_status) VALUES('','"+messageId+"','"+messageTitle+"','"+messageType+"','"+messageContent+"','"+appendixName+"',sysdate(),'"+operUserName+"','1')";
				logger.info(sql);
				session.add(sql);
				logger.info(toDeptNameArray[0]);
				
				for(int i=0;i<toDeptNameArray.length;i++){
					logger.info(toDeptNameArray[i]);
					sql="select dept_id,dept_name from sys_dept t WHERE t.dept_name = '"+toDeptNameArray[i]+"'" ;
					logger.info("查询部门号："+sql);
					List resultList = session.findSql(sql);
					Map resultMap = (Map)resultList.get(0);
					String toDeptId = resultMap.get("deptId").toString();
					String toDeptName1 = resultMap.get("deptName").toString();
					sql = "INSERT INTO message_read_detail(primary_key,message_id,dept_id,dept_name,read_time,read_user,read_status) VALUES('','"+messageId+"','"+toDeptId+"','"+toDeptNameArray[i]+"','','','1')";
System.out.println(sql);					
					session.add(sql);
				}
				
                   sql = "select message_id  from message_read_detail where message_id ='"+messageId+"'and dept_id ='1'";
				List resultList1 = session.findSql(sql);
System.out.println(resultList1.size()==0);
				if(resultList1.size()==0)
				{
					sql = "INSERT INTO message_read_detail(primary_key,message_id,dept_id,dept_name,read_time,read_user,read_status) VALUES('','"+messageId+"','1','管理平台','','','1')";
					System.out.println(sql);					
				 session.add(sql);
				}
					session.closeSession();
					resultStr = "success";
				
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
