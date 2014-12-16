package com.cqupt.pub.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class descriptionLeftQueryAction extends ActionSupport{
	Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;	
	
	public String execute(){
		logger.info("descriptionLeftQueryAction");
			request=ServletActionContext.getRequest();
		
			String brandId = request.getParameter("supplierName");
			String gadgetsCode = request.getParameter("gadgetsCode");
			String gadgetsDescription = request.getParameter("gadgetsDescription");
			String deptId = request.getSession().getAttribute("deptId").toString();
			try{
				if(brandId != null){
					brandId = java.net.URLDecoder.decode(brandId, "UTF-8");
				}else brandId = "";
				if(gadgetsCode != null){
					gadgetsCode = java.net.URLDecoder.decode(gadgetsCode, "UTF-8");
				}else gadgetsCode = "";
				if(gadgetsDescription != null){
					gadgetsDescription = java.net.URLDecoder.decode(gadgetsDescription, "UTF-8");
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
		        out.println(getList(brandId,gadgetsCode,gadgetsDescription,	deptId));    
		        out.flush();    
		        out.close();    
			} catch (IOException e) {
				e.printStackTrace();
			}    

	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

	}
	
	public String getList(String brandId,String gadgetsCode,String gadgetsDescription,String deptId){
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;
		try{
			session = DataStormSession.getInstance();
			sql = "select @row := @row +1 as id,t.* from (SELECT @row:=0) r ,(SELECT  count(*) text FROM cqmass.gadgets_storage_in b where  b.dept_id='"+deptId+"' and b.gadgets_code='"+gadgetsCode+"' and b.gadgets_description='"+gadgetsDescription+"' and b.gadgets_state='1' and b.storeroom_type='良品库') t";			
System.out.println(sql);	
			List resultList =  session.findSql(sql);
			Map resultMap = (Map)resultList.get(0);
			resultStr = resultMap.get("text").toString();
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
