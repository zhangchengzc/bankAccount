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

public class CodeBrandVersionQueryAction extends ActionSupport{
	Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;	
	
	public String execute(){
			logger.info("CodeBrandVersionQueryAction");
			request=ServletActionContext.getRequest();
		
			String brandName = request.getParameter("brandName");
			String gadgetsDescription = request.getParameter("gadgetsDescription");
			String versionName = request.getParameter("versionName");

			try{
				if(brandName != null){
					brandName = java.net.URLDecoder.decode(brandName,"UTF-8");
				}else brandName = "良品库";
				if(gadgetsDescription != null){
					gadgetsDescription = java.net.URLDecoder.decode(gadgetsDescription,"UTF-8");
					//System.out.println("gadgetsDescription："+gadgetsDescription);
				}else gadgetsDescription = "";
				if(versionName != null){
					versionName = java.net.URLDecoder.decode(versionName,"UTF-8");
				}else versionName = "";
				
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
		        out.println(getList(brandName,gadgetsDescription,versionName));    
		        out.flush();    
		        out.close();    
			} catch (IOException e) {
				e.printStackTrace();
			}    

	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

	}
	
	public String getList(String brandName,String gadgetsDescription,String versionName){
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;
		try{
			session = DataStormSession.getInstance();
			/*SELECT a.gadgets_code FROM cqmass.pro_gadgets a,cqmass.pro_version_gadgets b 
			where a.brand_name='三星' and a.gadgets_description='电池' 
					and b.version_name='I519' and a.gadgets_code=b.gadgets_code;*/
			/*sql = "SELECT @row := @row +1 as id,t.gadgets_code text FROM (SELECT @row:=0) r ,cqmass.pro_gadgets t,cqmass.pro_version_gadgets b" +
					"  where t.brand_name='"+brandName+"' and t.gadgets_description='"+gadgetsDescription+"'" +
							" and t.gadgets_code=b.gadgets_code";
							if(versionName != ""){
				sql += " and b.version_name='"+versionName+"'";
			}*/
			sql = "SELECT @row := @row +1 as id,t.gadgets_code text FROM (SELECT @row:=0) r ,cqmass.pro_gadgets t" +
				"  where t.brand_name='"+brandName+"' and t.gadgets_description='"+gadgetsDescription+"'" +
						" and t.gadgets_code in (select distinct  a.gadgets_code from  cqmass.pro_version_gadgets a where  a.version_name = '"+versionName+"' order by a.gadgets_code)";
				//(select distinct  (t.gadgets_code) id, t.gadgets_description text from  cqmass.pro_version_gadgets t where  version_name = '"+versionName+"' order by gadgets_code) a
			
			logger.info("由品牌型号物料名称查厂编："+sql);	
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
