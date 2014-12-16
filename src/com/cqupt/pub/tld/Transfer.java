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
public class Transfer extends Component{
	
	Logger logger = Logger.getLogger(getClass());
	public Transfer(ValueStack stack) {
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
			logger.info("Transfer:");
			session = DataStormSession.getInstance();
			String sql = "SELECT ifnull(count(a.case_num),0) all_amount from cqmass.case_status a,cqmass.case_accept b WHERE a.dept_id='"+deptId+"' and a.is_transfer='需要物流' and a.case_num=b.case_num and b.case_status in ('1','3')" ;
			logger.info("统计工单待发物流sql:"+sql);
			List resultList = session.findSql(sql);
			String allAmount = ((Map)resultList.get(0)).get("allAmount").toString();
			
			sql = "SELECT ifnull(count(allocate_num),0) allo_num,ifnull(sum(amount),0) allocate_amount from cqmass.allocate where from_dept_id='"+deptId+"' and allocate_status = '物流待发'";
			logger.info("统计调拨单待发物流sql:"+sql);
			List allocateList = session.findSql(sql);
			String alloNum = ((Map)allocateList.get(0)).get("alloNum").toString();
			String allocateAmount = ((Map)allocateList.get(0)).get("allocateAmount").toString();		
			
			sql = "select ifnull(count(*),0) ci_amount from cqmass.gadgets_storage_in where dept_id='"+deptId+"' and gadgets_state='1' and storeroom_type='次品库'";
			logger.info("统计次品库sql:"+sql);
			List ciList = session.findSql(sql);
			
			sql = "select ifnull(count(*),0) bao_amount from cqmass.gadgets_storage_in where dept_id='"+deptId+"' and gadgets_state='1' and storeroom_type='三包库'";
			logger.info("统计三包库sql:"+sql);
			List baoList = session.findSql(sql);
			
			sql = "select ifnull(count(*),0) fei_amount from cqmass.gadgets_storage_in where dept_id='"+deptId+"' and gadgets_state='1' and storeroom_type='废品库'";
			logger.info("统计废品库sql:"+sql);
			List feiList = session.findSql(sql);
			
			sb.append("<TABLE BORDER=\"0\" CELLSPACING=\"5\" CELLPADDING=\"0\" VSPACE=\"0\" HSPACE=\"0\" BORDERCOLOR=\"#B7B4B5\">");
			//工单物流
			sb.append("<TR> <TD COLSPAN=\"2\"> ");
			if(allAmount.equals("0")) {				
				remaindText = "暂无工单  待发物流";
				sb.append("<a style=\"text-decoration:none ;color: #010882;\"><span class=\"STYLE3\">" + remaindText+"</span></marquee>");
			} else {
				
					
			//	System.out.println("allAmount:"+allAmount);	 onclick=\"addTab(\"工单物流发起\",\"transferBegin\",\"transferBegin\")\"				
					sb.append("<a href=\"#\" onclick=$(window.parent.window).get(0).addTab('网点物流发起','innerTransferBeginAcion','transferBegin') >");					
					sb.append("<span class=\"STYLE3\" >");
					sb.append("有  "+allAmount+"  条工单  待发物流");
					sb.append("&nbsp;&nbsp;");
					
					sb.append("<font color=\"red\"><span class=\"STYLE4\">     </span></span>&nbsp;</a><BR>");				
				
			}
			sb.append("</TD></TR> ");
			//调拨单物流
			sb.append("<TR> <TD COLSPAN=\"2\"> ");
			if(alloNum.equals("0")) {				
				remaindText = "暂无调拨单  待发物流";
				sb.append("<a style=\"text-decoration:none ;color: #010882;\"><span class=\"STYLE3\">" + remaindText+"</span></marquee>");
			} else {
								
					
					sb.append("<a href=\"#\" onclick=$(window.parent.window).get(0).addTab('网点物流发起','innerTransferBeginAcion','transferBegin') >");					
					sb.append("<span class=\"STYLE3\" >");
					sb.append("有   "+alloNum+"  条调拨单  "+allocateAmount+" 个商品  待发物流");
					sb.append("&nbsp;&nbsp;");					
					sb.append("<font color=\"red\"><span class=\"STYLE4\">     </span></span>&nbsp;</a>");				
				
			}
			sb.append("</TD></TR> ");
			//旧品统计
			sb.append("<TR> <TD COLSPAN=\"2\"> ");
			
			String ciAmount = ((Map)ciList.get(0)).get("ciAmount").toString();
			String baoAmount = ((Map)baoList.get(0)).get("baoAmount").toString();	
			String feiAmount = ((Map)feiList.get(0)).get("feiAmount").toString();	
			
			sb.append("<a href=\"#\" onclick=$(window.parent.window).get(0).addTab('网点物流发起','innerTransferBeginAcion','transferBegin') >");					
			sb.append("<span class=\"STYLE3\" >");
			sb.append("有   "+ciAmount+"  个次品  "+baoAmount+" 个三包品 "+feiAmount+" 个废品  待发物流");
			sb.append("&nbsp;&nbsp;");					
			sb.append("<font color=\"red\"><span class=\"STYLE4\">     </span></span>&nbsp;</a>");
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


