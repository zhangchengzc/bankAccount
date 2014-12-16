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

public class VersionQueryBrandNameAction extends ActionSupport{
Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;	
	
	public String execute(){
		logger.info("VersionQueryBrandNameAction");
			request=ServletActionContext.getRequest();
		
			String brandName = request.getParameter("brandName");
			try{
				if(brandName != null){
					brandName = java.net.URLDecoder.decode(brandName,"UTF-8");
				}else brandName = "";
				
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
		        out.println(getList(brandName));    
		        out.flush();    
		        out.close();    
			} catch (IOException e) {
				e.printStackTrace();
			}    

	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

	}
	
	public String getList(String brandName){
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;
		try{
			session = DataStormSession.getInstance();
			sql = "select t.version_id id,t.version_name text from cqmass.pro_version t where t.brand_name='"+brandName+"'";			
logger.info(sql);	
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
