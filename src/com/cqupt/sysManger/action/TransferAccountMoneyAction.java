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
public class TransferAccountMoneyAction extends ActionSupport{
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
				String userId = (String)session.getAttribute("userId");
				String saveMoney = java.net.URLDecoder.decode(request.getParameter("saveMoney"), "UTF-8");
				String cardNumber = java.net.URLDecoder.decode(request.getParameter("cardNumber"), "UTF-8");
				HttpServletResponse response=ServletActionContext.getResponse();
		        response.setCharacterEncoding("UTF-8");    
		        PrintWriter out;
		        out = response.getWriter();
				out.print(insertUserinfo( userId, saveMoney,cardNumber));    
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
		
		
		private String insertUserinfo(String userId,String saveMoney,String cardNumber) {
			DataStormSession session = null;
			String resultStr = "";
			String sql= "";
			
			try {
				session = DataStormSession.getInstance();
				sql = "update sys_user set remark = remark -"+saveMoney+" where user_id = '"+userId+"'";
				session.update(sql);
				sql = " update money_draw set draw_money = draw_money-"+saveMoney+" where card_number='"+userId+"'";
				session.update(sql);
				sql = " update money_save set save_money = save_money-"+saveMoney+" where card_number='"+userId+"'";
				session.update(sql);
				sql="insert into money_drawdetail (date,card_number,draw_money) values(sysdate(),'"+userId+"','"+saveMoney+"') ";
				session.add(sql);
				sql = "update sys_user set remark = remark +"+saveMoney+" where user_id = '"+cardNumber+"'";
				session.update(sql);
				sql = " update money_draw set draw_money = draw_money+"+saveMoney+" where card_number='"+cardNumber+"'";
				session.update(sql);
				sql = " update money_save set save_money = save_money+"+saveMoney+" where card_number='"+cardNumber+"'";
				session.update(sql);
				sql="insert into money_savedetail (date,card_number,save_money) values(sysdate(),'"+cardNumber+"','"+saveMoney+"') ";
				session.add(sql); 
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
