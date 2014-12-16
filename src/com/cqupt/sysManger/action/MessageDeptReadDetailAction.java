package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.opensymphony.xwork2.ActionSupport;


public class MessageDeptReadDetailAction  extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2465087541585226388L;
	/**
	 *   查询本部门用户信息
	 */
	Logger logger =  Logger.getLogger(this.getClass());

	HttpServletRequest request = null;
	public String execute(){
		System.out.println("MessageDeptReadDetailAction：");
					request=ServletActionContext.getRequest();
					HttpSession session = request.getSession();
				//	String deptId = (String)session.getAttribute("deptId");
				//	System.out.println(deptId);
					String messageId = request.getParameter("messageId");
					String pageSize = request.getParameter("pagesize");
					String page = request.getParameter("page");
					HttpServletResponse response=ServletActionContext.getResponse();
			           //设置字符集    
		           response.setCharacterEncoding("UTF-8");    
		           PrintWriter out;
					try {
						if(messageId != null){
							messageId = java.net.URLDecoder.decode(messageId, "UTF-8");
						}else	messageId = "";
						
						out = response.getWriter();
						   //直接输入响应的内容    
				        out.println(getUserList(pageSize, page,messageId));    
				        out.flush();    
				        out.close();    
					} catch (IOException e) {
						e.printStackTrace();
					}    

			       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

			}
			
	private char[] getUserList(String pageSize, String page,String messageId){
				String resultStr = "";
				String sql = "";
				DataStormSession session = null;
				try{
					session = DataStormSession.getInstance();
						 sql = "select @rownum:=@rownum+1 AS rownum, a.dept_name,a.read_user,date_format(a.read_time,'%Y-%c-%d %H:%i:%s') read_time,a.read_status" +
							"  from (SELECT @rownum:=0) r ,cqmass.message_read_detail a where a.message_id ='"+messageId+"'";					 
			logger.info(sql);	
			Map resultMap = session.findSql(sql, Integer.parseInt(page), Integer.parseInt(pageSize));
			resultStr = JsonUtil.map2json(resultMap);
System.out.println("resultStr:"+resultStr);
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
				System.out.println(resultStr);
				return resultStr.toCharArray();
				
			}

		}
