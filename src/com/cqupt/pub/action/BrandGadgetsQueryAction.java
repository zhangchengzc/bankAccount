package com.cqupt.pub.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class BrandGadgetsQueryAction extends ActionSupport{
	Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;	
	
	public String execute(){
		logger.info("BrandGadgetsQueryAction");
			request=ServletActionContext.getRequest();
		
			String supplierName = request.getParameter("supplierName");

			try{
				if(supplierName != null){
					supplierName = java.net.URLDecoder.decode(supplierName, "UTF-8");
				}else supplierName = "";

			}catch(UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			
			HttpServletResponse response=ServletActionContext.getResponse();
	           //设置字符集    
           response.setCharacterEncoding("UTF-8");    
           PrintWriter out;
			try {
				out = response.getWriter();
				   //直接输入响应的内容    
		        out.println(getList(supplierName));    
		        out.flush();    
		        out.close();    
			} catch (IOException e) {
				e.printStackTrace();
			}    

	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

	}
	
	public String getList(String supplierName){
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;
		try{
			session = DataStormSession.getInstance();
			sql = "select @row := @row +1 as id,t.* from (SELECT @row:=0) r ,(SELECT distinct b.gadgets_description text FROM cqmass.pro_gadgets b where  b.supplier_name='"+supplierName+"') t";			
System.out.println(sql);	
			JSONArray jsonObject = JSONArray.fromObject(session.findSql(sql));
			resultStr = jsonObject.toString();
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
		return resultStr;

	}

}
