package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.JsonUtil;
import com.opensymphony.xwork2.ActionSupport;


public class UserManagerQueryAction  extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2465087541585226388L;
	/**
	 *   查询本部门用户信息
	 */
	HttpServletRequest request = null;
	public String execute(){
		System.out.println("UserManagerQueryAction：");
					request=ServletActionContext.getRequest();
					HttpSession session = request.getSession();
					String deptId = (String)session.getAttribute("deptId");
					String userId = (String)session.getAttribute("userId");
					System.out.println(deptId);
					String pageSize = request.getParameter("pagesize");
					String page = request.getParameter("page");
					HttpServletResponse response=ServletActionContext.getResponse();
			           //设置字符集    
		           response.setCharacterEncoding("UTF-8");    
		           PrintWriter out;
					try {
						out = response.getWriter();
						   //直接输入响应的内容    
				        out.println(getUserList(deptId, userId,pageSize, page));    
				        out.flush();    
				        out.close();    
					} catch (IOException e) {
						e.printStackTrace();
					}    

			       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

			}
			
	private char[] getUserList(String deptId ,String userId,String pageSize, String page){
				String resultStr = "";
				String sql = "";
				DataStormSession session = null;
				try{
					session = DataStormSession.getInstance();
					sql = "select b.* , ROWNUM order_id FROM (select @rownum:=@rownum+1 as rownum,t.remark,t.user_id , t.user_name , t.dept_name , t.group_name , t.user_state, ifnull(t.user_email,' ') user_email, ifnull(t.phone_num,' ') user_mob_phone "+
				 	  " from (select @rownum:=0) r, bankaccount.sys_user t WHERE t.dept_id = '"+deptId+"'";
					if(!userId.equals("admin"))
					{
						sql+=" and user_id='"+userId+"'";
					}
					sql+=")b";
					System.out.println(sql);	
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
