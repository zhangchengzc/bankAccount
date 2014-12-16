package com.cqupt.pub.tld;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.struts2.components.Component;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.log4j.Logger;

public class PageADMOperAuth extends Component{
	private String menuId;
	private String userId;
	Logger logger = Logger.getLogger(getClass());

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public PageADMOperAuth(ValueStack stack) {
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
		//resultStr.append("<script type=\"text/javascript\">");
		//resultStr.append(" $(\"#toptoolbar\").ligerToolBar({ items: [{ text: '增加商品信息', id:'add', icon:'add',click: f_add },{ text: '修改选中商品信息', id:'modify', icon:'modify',click: f_modify },{ text: '删除选中商品信息', id:'delete', icon:'delete',click: f_delete } ] }); ");

           
		DataStormSession session = null;
		try {
			session = DataStormSession.getInstance();			
			String sql = "select  c.icon,c.url,c.menuname from sys_user a,sys_user_group_oper_auth b,sys_menu c WHERE a.group_id = b.group_id AND a.user_id ='"+this.getUserId()+"' AND b.menuid = c.menuid AND b.menuid IN ("+this.getMenuId()+") order by c.menuid" ;
			logger.info("读取三级菜单："+sql);
			List resultList = session.findSql(sql);
			if (resultList.size() != 0){
				for (int i = 0; i < resultList.size(); i++) {
					Map resultMap = (Map) resultList.get(i);
					if(i==0) {
						resultStr.append("toolBar={ items: [{ text: '"+resultMap.get("menuname")+"', id:'"+resultMap.get("icon")+"', icon:'"+resultMap.get("icon")+"',click: "+resultMap.get("url")+"}");
					} else {
						resultStr.append(" { text: '"+resultMap.get("menuname")+"', id:'"+resultMap.get("icon")+"', icon:'"+resultMap.get("icon")+"',click: "+resultMap.get("url")+"}");
					}
					if(i!=(resultList.size()-1)){
						resultStr.append(",");
					}else {
						resultStr.append(" ]} ");
					}
				}
			}else{
				resultStr.append("toolBar = { items: [] }");
			}
			
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
		return resultStr.toString();
	}

}
