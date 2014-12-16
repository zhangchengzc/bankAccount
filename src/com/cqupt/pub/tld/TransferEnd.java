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

public class TransferEnd extends Component{
	
	Logger logger = Logger.getLogger(getClass());
	public TransferEnd(ValueStack stack) {
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
			logger.info("TransferEnd:");
			session = DataStormSession.getInstance();
			//select ifnull(count(*),0) transfer_amount,ifnull(sum(t.case_amount),0) case_amount from (SELECT b.transfer_id,count(*) case_amount FROM cqmass.transfer_list a right join cqmass.transfer_case_detail b
			// on a.transfer_id=b.transfer_id and a.transfer_state='待接收' and a.to_dept_id='1' group by b.transfer_id) t;
			String sql = "SELECT ifnull(count(*),0) transfer_amount FROM cqmass.transfer_list where transfer_state='待接收' and to_dept_id='"+deptId+"'";
			logger.info("统计待接收物流总数sql:"+sql);
			List resultList = session.findSql(sql);
			//物流单数
			String transferAmount = ((Map)resultList.get(0)).get("transferAmount").toString();
			
								
			sql = "select ifnull(sum(t.case_amount),0) case_amount from (SELECT b.transfer_id,count(b.case_num) case_amount FROM cqmass.transfer_list a ,cqmass.transfer_case_detail b " +
					" where a.transfer_id=b.transfer_id and b.product_state='待接收' and a.to_dept_id='"+deptId+"' group by b.transfer_id) t";
					
			logger.info("统计工单待接收物流sql:"+sql);
			resultList = session.findSql(sql);			
			String caseAmount = ((Map)resultList.get(0)).get("caseAmount").toString();//工单数
			
			
			sql = "SELECT ifnull(count(allocate_num),0) allo_num,ifnull(sum(amount),0) allocate_amount from cqmass.allocate where to_dept_id='"+deptId+"' and allocate_status = '物流发起'";
			logger.info("统计调拨单待接收物流sql:"+sql);
			List allocateList = session.findSql(sql);
			String alloNum = ((Map)allocateList.get(0)).get("alloNum").toString();
			String allocateAmount = ((Map)allocateList.get(0)).get("allocateAmount").toString();		
			
			/*sql = "select ifnull(count(*),0) ci_amount from cqmass.gadgets_storage_in where dept_id='"+deptId+"' and gadgets_state='1' and storeroom_type='次品库'";
			logger.info("统计次品库sql:"+sql);
			List ciList = session.findSql(sql);
			
			sql = "select ifnull(count(*),0) bao_amount from cqmass.gadgets_storage_in where dept_id='"+deptId+"' and gadgets_state='1' and storeroom_type='三包库'";
			logger.info("统计三包库sql:"+sql);
			List baoList = session.findSql(sql);
			
			sql = "select ifnull(count(*),0) fei_amount from cqmass.gadgets_storage_in where dept_id='"+deptId+"' and gadgets_state='1' and storeroom_type='废品库'";
			logger.info("统计废品库sql:"+sql);
			List feiList = session.findSql(sql);
			*/
			sql = "select ifnull(sum(t.gadgets_amount),0) gadgets_all_amount from" +
					" (SELECT b.transfer_id,b.gadgets_amount FROM cqmass.transfer_list a ,cqmass.transfer_gadgets_detail b" +
					" where a.transfer_id=b.transfer_id and b.product_state='待接收' and a.to_dept_id='"+deptId+"' " +
					" and b.storeroom_type!='良品库' group by b.transfer_id) t";
			logger.info("统计旧料待接收物流sql:"+sql);
			List oldList = session.findSql(sql);
			Map oldMap = (Map)oldList.get(0);
			String gadgetsAllAmount = oldMap.get("gadgetsAllAmount").toString();
		//String listAmount = oldMap.get("listAmount").toString();
			
			
			
			sb.append("<TABLE BORDER=\"0\" CELLSPACING=\"5\" CELLPADDING=\"0\" VSPACE=\"0\" HSPACE=\"0\" BORDERCOLOR=\"#B7B4B5\">");
			//工单物流
			
			if(transferAmount.equals("0")) {	
				
				sb.append("<TR> <TD COLSPAN=\"2\"> ");
				remaindText = "暂无待接收物流";
				sb.append("<a style=\"text-decoration:none ;color: #010882;\"><span class=\"STYLE3\">" + remaindText+"</span></marquee>");
				sb.append("</TD></TR> ");
			} else {
				//总物流单
			sb.append("<TR> <TD COLSPAN=\"2\"> ");
				sb.append("<a href=\"#\" onclick=$(window.parent.window).get(0).addTab('网点物流接收','innerTransferAcceptAcion','transferEnd') >");					
				sb.append("<span class=\"STYLE3\" >");
				sb.append("共有   "+transferAmount+"  条物流待接收物流");
				sb.append("&nbsp;&nbsp;");
				
				sb.append("<font color=\"red\"><span class=\"STYLE4\">     </span></span>&nbsp;</a><BR>");	
			
		
	   	    sb.append("</TD></TR> ");
				
				//维修工单物流
			sb.append("<TR> <TD COLSPAN=\"2\"> ");
					sb.append("<a href=\"#\" onclick=$(window.parent.window).get(0).addTab('网点物流接收','innerTransferAcceptAcion','transferEnd') >");					
					sb.append("<span class=\"STYLE3\" >");
					sb.append("其中  有 "+caseAmount+"  条维修工单  待接收物流");
					sb.append("&nbsp;&nbsp;");
					
					sb.append("<font color=\"red\"><span class=\"STYLE4\">     </span></span>&nbsp;</a><BR>");	
				
			
			sb.append("</TD></TR> ");
			//调拨单物流
					sb.append("<TR> <TD COLSPAN=\"2\"> ");
			
					sb.append("<a href=\"#\" onclick=$(window.parent.window).get(0).addTab('网点物流接收','innerTransferAcceptAcion','transferEnd') >");					
					sb.append("<span class=\"STYLE3\" >");
					sb.append("有   "+alloNum+"  条调拨单  共"+allocateAmount+" 个商品  待接收物流");
					sb.append("&nbsp;&nbsp;");					
					sb.append("<font color=\"red\"><span class=\"STYLE4\">     </span></span>&nbsp;</a>");				
				
			
			sb.append("</TD></TR> ");
			//旧品待接收物流统计
			sb.append("<TR> <TD COLSPAN=\"2\"> ");
				
				
				sb.append("<a href=\"#\"");					
				sb.append("<span class=\"STYLE3\" >");
				sb.append("有   "+gadgetsAllAmount+"  个旧品  待接收物流");
				sb.append("&nbsp;&nbsp;");					
				sb.append("<font color=\"red\"><span class=\"STYLE4\">     </span></span>&nbsp;</a>");
			sb.append("</TD></TR> ");
			
			}
			
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


