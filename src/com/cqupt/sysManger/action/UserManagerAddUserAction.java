package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.Md;
import com.opensymphony.xwork2.ActionSupport;

public class UserManagerAddUserAction  extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6941574699319426537L;

	/**
		 * 
		 */
		HttpServletRequest request=null;
		private Md md5fun = new Md();

		public String execute()
		{
			
			

			this.request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			
			try {
				String txtDeptId = (String)session.getAttribute("deptId");
				String txtUserId = java.net.URLDecoder.decode(request.getParameter("txtUserId"), "UTF-8");
				String txtName = java.net.URLDecoder.decode(request.getParameter("txtName"), "UTF-8");
				String txtPsw = java.net.URLDecoder.decode(request.getParameter("txtPsw"), "UTF-8");
				String txtPhone = java.net.URLDecoder.decode(request.getParameter("txtPhone"), "UTF-8");
				String txtEmail = java.net.URLDecoder.decode(request.getParameter("txtEmail"), "UTF-8");
				String isUseable = java.net.URLDecoder.decode(request.getParameter("isUseable"), "UTF-8");
				String remark = java.net.URLDecoder.decode(request.getParameter("remark"), "UTF-8");
				if(isUseable.equals("1")){
					 isUseable = "可用";
				}else{
					isUseable = "禁用";
				}
				
				System.out.println(isUseable);
				HttpServletResponse response=ServletActionContext.getResponse();
		          
		        response.setCharacterEncoding("UTF-8");    
		        PrintWriter out;
		        out = response.getWriter();
				out.print(insertUserinfo( txtDeptId, txtUserId, txtName,  txtPsw,  txtPhone, txtEmail, isUseable,remark));    
		        out.flush();    
		        out.close();  
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}    
	               
	       return null;


		}
		
		
		private String insertUserinfo(String txtDeptId,String txtUserId, String txtName,  String txtPsw, String txtPhone, String txtEmail, String isUseable,String remark) {
			DataStormSession session = null;
			String resultStr = "";
			String sql= "";
			
			try {
				session = DataStormSession.getInstance();
				
				sql = "select COUNT(*) COUNT from sys_user t WHERE t.user_id = '"+txtUserId+"'";
				List resultList = session.findSql(sql);
				Map resultMap = (Map)resultList.get(0);
				if(!resultMap.get("count").toString().equals("0")){
					session.closeSession();
					resultStr = "allExist";
				} else {
					sql = "INSERT INTO sys_user VALUES('"+txtUserId+"','"+txtName+"','"+md5fun.getMD5ofStr(txtPsw)+"','"+txtDeptId+"','',111,'用户','"+isUseable+"','"+txtEmail+"','"+txtPhone+"','"+remark+"',CURDATE())";
System.out.println(sql);					
					session.add(sql);
					sql = "insert into money_save (date,card_number,save_money) values(CURDATE(),'"+txtUserId+"','"+remark+"')";
					session.add(sql);
					sql = "insert into money_draw (date,card_number,draw_money) values(CURDATE(),'"+txtUserId+"','"+remark+"')";
					session.add(sql);
					sql="insert into money_savedetail (date,card_number,save_money) values(sysdate(),'"+txtUserId+"','"+remark+"') ";
					session.add(sql);
					sql="insert into money_drawdetail (date,card_number,draw_money) values(sysdate(),'"+txtUserId+"','"+remark+"') ";
					session.add(sql);
					session.closeSession();
					resultStr = "success";
				}
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
