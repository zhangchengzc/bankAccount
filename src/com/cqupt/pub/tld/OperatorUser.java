package com.cqupt.pub.tld;

import java.io.IOException;
import java.io.Writer;

import net.sf.json.JSONArray;

import org.apache.struts2.components.Component;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.util.ValueStack;

public class OperatorUser extends Component{
	
	private String deptId;
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	

	public OperatorUser(ValueStack stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}
	
	public boolean start(Writer writer){
		boolean result = super.start(writer);
		try{
			String str = getList();
			writer.write(str);
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	private String getList(){
		StringBuilder resultStr = new StringBuilder();
		resultStr.append("<script type=\"text/javascript\">");
		resultStr.append("var OperatorData = ");
		
           
		DataStormSession session = null;
		JSONArray jsonObjectType = null;
		
		try {
			session = DataStormSession.getInstance();
			String sql = "select t.user_id id,t.user_name text from cqmass.sys_user t where t.dept_id='"+this.getDeptId()+"' and t.group_name='维修工程师'";
System.out.println("sql:"+sql);
			jsonObjectType = JSONArray.fromObject(session.findSql(sql));
			
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
		resultStr.append(jsonObjectType.toString());
		
		resultStr.append("</script>");
System.out.println(resultStr.toString());
		return resultStr.toString();
	}



}
