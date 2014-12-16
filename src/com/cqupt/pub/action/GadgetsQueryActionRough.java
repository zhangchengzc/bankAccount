package com.cqupt.pub.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import sun.jdbc.rowset.CachedRowSet;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class GadgetsQueryActionRough extends ActionSupport{
Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;	
	//String txtBrand = null;
	public String execute(){
System.out.println("GadgetsQueryActionRough");
			request=ServletActionContext.getRequest();
		
			//txtBrand = java.net.URLDecoder.decode(request.getParameter("txtBrand"),"UTF-8");
			
			
			
			HttpServletResponse response=ServletActionContext.getResponse();
	           //设置字符集    
           response.setCharacterEncoding("UTF-8");    
           PrintWriter out;
			try {
				out = response.getWriter();
				   //直接输入响应的内容    
		        out.println(getList());    
		        out.flush();    
		        out.close();    
			} catch (IOException e) {
				e.printStackTrace();
			}    

	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

	}
	
	public String getList(){
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;
		CachedRowSet rowset = null;
		StringBuffer sb =new StringBuffer();
		try{
			session = DataStormSession.getInstance();
			sql = "select t.gadgets_description label,t.gadgets_description value from cqmass.pro_gadgets t order by brand_name";			
			logger.info("物料模糊查询："+sql);	
			JSONArray jsonObject = JSONArray.fromObject(session.findSql(sql));
			resultStr = jsonObject.toString();
		/*	rowset = (CachedRowSet)session.findExecute(sql);
			while(rowset != null && rowset.next()){
				sb.append("\"");
				sb.append(rowset.getString("text"));
				sb.append("@");
				sb.append(rowset.getString("id"));
				//sb.append(")\"");
				sb.append("\"");
				sb.append(",");
			}
			resultStr = "["+sb.toString().substring(0,sb.length()-1)+"]";*/
		}catch (CquptException ce) {
			ce.printStackTrace();
		}finally {
			if (session != null) {
				try {
					session.closeSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
System.out.println("resultStr:"+resultStr);
		return resultStr;

	}

}

