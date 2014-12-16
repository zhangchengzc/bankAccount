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

public class saledescriptionCodeQueryAction extends ActionSupport{
	Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;	
	
	public String execute(){
			logger.info("DescriptionCodeQueryAction");
			request=ServletActionContext.getRequest();
		
			String supplierName = request.getParameter("supplierName");
			String gadgetsDescription = request.getParameter("gadgetsDescription");
			try{
				if(supplierName != null){
					supplierName = java.net.URLDecoder.decode(supplierName,"UTF-8");
				}else supplierName = "良品库";
				if(gadgetsDescription != null){
					gadgetsDescription = java.net.URLDecoder.decode(gadgetsDescription,"UTF-8");
				}else gadgetsDescription = "";
				
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
		        out.println(getList(supplierName,gadgetsDescription));    
		        out.flush();    
		        out.close();    
			} catch (IOException e) {
				e.printStackTrace();
			}    

	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

	}
	
	public String getList(String supplierName,String gadgetsDescription){
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;
		try{
			session = DataStormSession.getInstance();
			sql = "SELECT @row := @row +1 as id,t.gadgets_code text FROM (SELECT @row:=0) r ,cqmass.pro_gadgets t where t.supplier_name='"+supplierName+"' and t.gadgets_description='"+gadgetsDescription+"';";			
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
