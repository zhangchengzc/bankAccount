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


public class MessageManagerQueryAction  extends ActionSupport{

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
		System.out.println("MessageManagerQueryAction：");
					request=ServletActionContext.getRequest();
					HttpSession session = request.getSession();
				//	String deptId = (String)session.getAttribute("deptId");
				//	System.out.println(deptId);
					String messageType = request.getParameter("messageType");
					String messageTitle = request.getParameter("messageTitle");				
					String txtBeginDate = request.getParameter("txtBeginDate");
					String txtEndDate = request.getParameter("txtEndDate");
					String pageSize = request.getParameter("pagesize");
					String page = request.getParameter("page");
					String deptId = request.getSession().getAttribute("deptId").toString();
					HttpServletResponse response=ServletActionContext.getResponse();
			           //设置字符集    
		           response.setCharacterEncoding("UTF-8");    
		           PrintWriter out;
					try {
						if(messageType != null){
							messageType = java.net.URLDecoder.decode(messageType, "UTF-8");
						}else	messageType = "";
						if(messageTitle != null){
							messageTitle = java.net.URLDecoder.decode(messageTitle, "UTF-8");
						}else messageTitle = "";
						if(txtBeginDate != null){
							txtBeginDate = java.net.URLDecoder.decode(txtBeginDate, "UTF-8");
						}else	txtBeginDate = "";
						
						if(txtEndDate != null){
							txtEndDate = java.net.URLDecoder.decode(txtEndDate, "UTF-8");
						}else txtEndDate = "";

					if(txtEndDate.equals("")) {
						Calendar cal = Calendar.getInstance(); 

						SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");
						             //当前月的最后一天   
						cal.set( Calendar.DATE, 1 );
						cal.roll(Calendar.DATE, - 1 );
						Date endTime=cal.getTime();
						txtEndDate =datef.format(endTime);
						            
						            
					}
					
					if(txtBeginDate.equals("")) {
						Calendar cal = Calendar.getInstance(); 

						SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");        
						//当前月的第一天          
						cal.set(GregorianCalendar.DAY_OF_MONTH, 1); 
						Date beginTime=cal.getTime();
						txtBeginDate =datef.format(beginTime);
					}
						out = response.getWriter();
						   //直接输入响应的内容    
				        out.println(getUserList(messageType,messageTitle,pageSize, page,txtBeginDate,txtEndDate,deptId));    
				        out.flush();    
				        out.close();    
					} catch (IOException e) {
						e.printStackTrace();
					}    

			       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

			}
			
	private char[] getUserList(String messageType,String messageTitle,String pageSize, String page,String txtBeginDate,String txtEndDate,String deptId){
				String resultStr = "";
				String sql = "";
				DataStormSession session = null;
				try{
					session = DataStormSession.getInstance();
						 //当部门为管理部时，所有公告都需要显示
						 sql = "select @rownum:=@rownum+1 AS rownum, a.message_id,a.message_type,a.message_title,date_format(a.send_time,'%Y-%c-%d %H:%i:%s') send_time,a.message_content,a.appendix_name,a.send_user,b.read_status" +
									"  from (SELECT @rownum:=0) r ,bankaccount.message_detail a , bankaccount.message_read_detail b where message_status = '1' and a.message_id = b.message_id and  b.dept_id ='"+deptId+"'";
					if(!txtBeginDate.equals("")&&!txtBeginDate.equals("全部")){
						sql += " and a.send_time >= '"+txtBeginDate+" 00:00:00'";
					} 
					if(!txtEndDate.equals("")&&!txtEndDate.equals("全部")){
						sql += " AND a.send_time <= '"+txtEndDate+" 24:00:00'";
					} 
					if(!messageType.equals("")&&!messageType.equals("全部")){
						sql += " AND a.message_type = '"+messageType+"'";
					} 
					if(!messageTitle.equals("")&&!messageTitle.equals("全部")){
						sql += " AND a.message_title like '%"+messageTitle.toUpperCase()+"%'";
					}
					sql +=" group by b.message_id";
		logger.info(sql);	
		session = DataStormSession.getInstance();
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
