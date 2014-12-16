package com.cqupt.pub.tld;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Component;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.log4j.Logger;
public class MessageTask extends Component{
	
	Logger logger = Logger.getLogger(getClass());
	public MessageTask(ValueStack stack) {
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
		StringBuilder sb = new StringBuilder();
		String remaindText = null;

		HttpServletRequest request=ServletActionContext.getRequest();
        String deptId = request.getSession().getAttribute("deptId").toString();
		DataStormSession session = null;
		
		try {
			
			session = DataStormSession.getInstance();
			String sql = "SELECT CAST(ifnull(sum(b.read_status),0) as decimal(5, 0)) total_num from cqmass.message_detail a,cqmass.message_read_detail b WHERE b.read_status = '1' and b.dept_id='"+deptId+"'and a.message_id = b.message_id and a.message_status = '1'" ;
			logger.info("统计未读公告sql:"+sql);
			List resultList = session.findSql(sql);
			Map resultMap = (Map)resultList.get(0);
			String totalNum = resultMap.get("totalNum").toString();
			logger.info("未读公告条数"+totalNum);
			sb.append("<TABLE BORDER=\"0\" CELLSPACING=\"5\" CELLPADDING=\"0\" VSPACE=\"0\" HSPACE=\"0\" BORDERCOLOR=\"#B7B4B5\">");
			//公告条数
			sb.append("<TR> <TD COLSPAN=\"2\"> ");
			
			
			if(totalNum.equals("0")||totalNum.equals("0.0")) {				
				remaindText = "暂无未读公告";
				sb.append("<a style=\"text-decoration:none ;color: #010882;\"><span class=\"STYLE3\">" + remaindText+"</span></marquee>");
			}else{
				sb.append("<a href=\"#\" onclick=$(window.parent.window).get(0).addTab('公告管理','messageManageAction','messageManage') >");					
				sb.append("<span class=\"STYLE3\" >");
				sb.append("有  "+totalNum+"  条公告未读");
				
				sb.append("</TD></TR><TR><TD><span class=\"STYLE3\" >");
				
				sb.append("<MARQUEE DIRECTION=up WIDTH=400 HEIGHT=100 ");
				sb.append("SCROLLAMOUNT=1 SCROLLDELAY=1 style=\"text-decoration:none ;color: #010882;\" ");
				sb.append("ONMOUSEOVER=this.scrollDelay=600 ");
				sb.append("ONMOUSEOUT=this.scrollDelay=1 > ");
				sql = "SELECT b.message_id,a.message_content,a.message_title,a.message_type,date_format(a.send_time,'%Y-%c-%d') send_time,a.send_user from cqmass.message_detail a,cqmass.message_read_detail b WHERE b.read_status = '1' and b.dept_id='"+deptId+"'and a.message_id = b.message_id and a.message_status = '1'" ;
			     logger.info(sql);
			    List messageList = session.findSql(sql);
			for(int m=0; m<messageList.size();m++){
				
				String messageType = ((Map)messageList.get(m)).get("messageType").toString();
				String messageTitle = ((Map)messageList.get(m)).get("messageTitle").toString();
				String sendTime = ((Map)messageList.get(m)).get("sendTime").toString();
				String sendUser = ((Map)messageList.get(m)).get("sendUser").toString();

				
				sb.append(messageType);
				//sb.append(" <a style=\"text-decoration:none ;color: #010882;\"> ");
				//sb.append("<span class=\"STYLE3\">");
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				sb.append(messageTitle);
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				sb.append(sendTime);
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				sb.append(sendUser);
				sb.append("<br/>");
				
			}
			
		
			sb.append("</marquee></span>");
		}
			sb.append("</TD></TR> </TABLE>");
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
	
		return sb.toString();
	}

}


