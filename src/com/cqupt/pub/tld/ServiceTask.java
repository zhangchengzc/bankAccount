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

public class ServiceTask extends Component{
	
	Logger logger = Logger.getLogger(getClass());
	public ServiceTask(ValueStack stack) {
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
        if(deptId.length()==1){
        	deptId = request.getSession().getAttribute("dataAuthId").toString();
        }
        String userId = request.getSession().getAttribute("userId").toString();
		DataStormSession session = null;
		
		try {
			logger.info("ServiceTask:");
			session = DataStormSession.getInstance();
			String sql = "SELECT ifnull(COUNT(*),0) check_amount FROM cqmass.case_status a,cqmass.case_accept b WHERE a.dept_id in ("+deptId+") and a.is_transfer='' and a.case_next_status='检测' and a.case_num = b.case_num and b.case_status in ('1','3')"  ;
			logger.info("统计待检测工单sql:"+sql);
			List resultList = session.findSql(sql);
			String checkAmount = ((Map)resultList.get(0)).get("checkAmount").toString();
			
			sql = "SELECT ifnull(COUNT(*),0) repair_amount FROM cqmass.case_status a,cqmass.case_accept b WHERE a.dept_id in ("+deptId+") and a.is_transfer='' and a.case_next_status='维修' and a.case_num = b.case_num and b.case_status in ('1','3')";
			logger.info("统计待维修工单sql:"+sql);
			List repairList = session.findSql(sql);
			String repairAmount = ((Map)repairList.get(0)).get("repairAmount").toString();
			
			sql = "SELECT ifnull(COUNT(*),0) back_amount FROM cqmass.case_status a,cqmass.case_accept b WHERE a.dept_id in ("+deptId+") and a.is_transfer='' and a.case_next_status='返回客户' and a.case_num = b.case_num and b.case_status in ('1','3')";
			logger.info("统计返回客户的工单sql:"+sql);
			List backList = session.findSql(sql);
			String backAmount = ((Map)backList.get(0)).get("backAmount").toString();
			
			sb.append("<TABLE BORDER=\"0\" CELLSPACING=\"5\" CELLPADDING=\"0\" VSPACE=\"0\" HSPACE=\"0\" BORDERCOLOR=\"#B7B4B5\">");
			//工单物流
			sb.append("<TR> <TD COLSPAN=\"2\"> ");
			
			
			if(checkAmount.equals("0")) {				
				remaindText = "暂无工单  待检测";
				sb.append("<a style=\"text-decoration:none ;color: #010882;\"><span class=\"STYLE3\">" + remaindText+"</span></marquee>");
			} else {
				
					
			//	System.out.println("allAmount:"+allAmount);	 onclick=\"addTab(\"工单物流发起\",\"transferBegin\",\"transferBegin\")\"				
					sb.append("<a href=\"#\" onclick=$(window.parent.window).get(0).addTab('检测维修','serviceCheck','Check') >");					
					sb.append("<span class=\"STYLE3\" >");
					sb.append("有  "+checkAmount+"  条工单  待检测");
					sb.append("&nbsp;&nbsp;");
					
					sb.append("</span>&nbsp;</a><BR>");				
				
			}
			sb.append("</TD></TR> ");
			
			sb.append("<TR> <TD COLSPAN=\"2\"> ");
			if(repairAmount.equals("0")) {				
				remaindText = "暂无工单  待维修";
				sb.append("<a style=\"text-decoration:none ;color: #010882;\"><span class=\"STYLE3\">" + remaindText+"</span></marquee>");
			} else {
				
					
					sb.append("<a href=\"#\" onclick=$(window.parent.window).get(0).addTab('检测维修','serviceCheck','Check') >");					
					sb.append("<span class=\"STYLE3\" >");
					sb.append("有   "+repairAmount+"  条工单  待维修");
					sb.append("&nbsp;&nbsp;");					
					sb.append("</span>&nbsp;</a>");				
				
			}
			sb.append("</TD></TR> ");

			sb.append("<TR> <TD COLSPAN=\"2\"> ");
			if(backAmount.equals("0")) {				
				remaindText = "暂无工单  待返回客户";
				sb.append("<a style=\"text-decoration:none ;color: #010882;\"><span class=\"STYLE3\">" + remaindText+"</span></marquee>");
			} else {
				
					
					sb.append("<a href=\"#\" onclick=$(window.parent.window).get(0).addTab('待取机','back2User','backUser') >");					
					sb.append("<span class=\"STYLE3\" >");
					sb.append("有   "+backAmount+"  条工单  待返回客户");
					sb.append("&nbsp;&nbsp;");					
					sb.append("<font color=\"red\"><span class=\"STYLE4\">     </span></span>&nbsp;</a>");				
				
			}
			sb.append("</TD></TR> ");
			sb.append("</TABLE>");
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


