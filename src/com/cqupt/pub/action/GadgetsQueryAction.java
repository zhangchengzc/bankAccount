package com.cqupt.pub.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class GadgetsQueryAction extends ActionSupport{
	Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;	
	
	public String execute(){

			request=ServletActionContext.getRequest();
		
			String versionId = request.getParameter("versionId");
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
		        out.println(getList(versionId,brandName));    
		        out.flush();    
		        out.close();    
			} catch (IOException e) {
				e.printStackTrace();
			}    

	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

	}
	
	public String getList(String versionId,String brandName){
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;
		try{
			session = DataStormSession.getInstance();
			sql = "select @row := @row +1 as id,t.* from (SELECT @row:=0) r ,(select distinct b.gadgets_description text" +
					" from cqmass.pro_version_gadgets a,cqmass.pro_gadgets b where a.gadgets_code = b.gadgets_code and a.version_id='"+versionId+"' and b.brand_name='"+brandName+"') t";			
logger.info("查询物料名称 ："+sql);
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

		return resultStr;

	}

}
