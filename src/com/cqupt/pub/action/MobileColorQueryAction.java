package com.cqupt.pub.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class MobileColorQueryAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;	
	
	public String execute(){
System.out.println("MobileColorQueryAction");
			request=ServletActionContext.getRequest();
		
			String versionId = request.getParameter("versionId");
System.out.println(versionId);
			
			
			HttpServletResponse response=ServletActionContext.getResponse();
	           //设置字符集    
           response.setCharacterEncoding("UTF-8");    
           PrintWriter out;
			try {
				out = response.getWriter();
				   //直接输入响应的内容    
		        out.println(getList(versionId));    
		        out.flush();    
		        out.close();    
			} catch (IOException e) {
				e.printStackTrace();
			}    

	       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

	}
	
	public String getList(String versionId){
		StringBuilder resultStr = new StringBuilder();
		String sql = "";
		DataStormSession session = null;
		try{
			session = DataStormSession.getInstance();
			sql = "select t.version_id id,t.color text from cqmass.pro_version t where t.version_id='"+versionId+"'";			
System.out.println(sql);	
			List resultList = session.findSql(sql);
			Map resultMap = (Map)resultList.get(0);
			String id = resultMap.get("id").toString();
			String text = resultMap.get("text").toString();
			String[] textArray = text.split("\\|");
			resultStr.append("[");
			int i;
			for(i=0; i<textArray.length-1; i++){
				System.out.println("textArray:"+textArray[i]);
				resultStr.append("{\"id\":\""+id+"\",\"text\":\""+textArray[i]+"\"},");
			}
			if(i==textArray.length-1){
				resultStr.append("{\"id\":\""+id+"\",\"text\":\""+textArray[i]+"\"}]");
			}
			//JSONArray jsonObject = JSONArray.fromObject(session.findSql(sql));
			//resultStr = jsonObject.toString();
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
System.out.println(resultStr.toString());
		return resultStr.toString();

	}

}
