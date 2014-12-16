package com.cqupt.pub.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class OrderOverDueAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(this.getClass());
	HttpServletRequest request = null;

	public String execute() {
		if("crowley".equals("crowley")) return SUCCESS;
		logger.info("OrderOverDueAction:)");
		request = ServletActionContext.getRequest();
		String deptId=request.getSession().getAttribute("deptId").toString();
		request.setAttribute("deptId", deptId);
		DataStormSession session = null;
		List list = null;
		long acceptToTransfer = 0;
		long acceptToCheck = 0;
		long checkToRepair = 0;
		long transferToReceive = 0;
		long doneRepairToTransfer = 0;
		long doneRepairToReturn = 0;
		String sql = "";
		String sqlPiece = "";
		if(deptId.equals("1")){
			sqlPiece = "";
		} else {
			sqlPiece = " and a.dept_id='" + deptId + "' ";
		}
		
		try {
			session = DataStormSession.getInstance();
			//受理到发物流（需要物流的）
			sql = "SELECT count(*) total FROM cqmass.case_status a, cqmass.case_accept b where a.case_num=b.case_num and b.case_status in ('1','3') and a.case_status='受理' and a.is_transfer='需要物流' " + sqlPiece + " and TIMESTAMPDIFF(HOUR,a.case_submit_time,sysdate())>24 ";
			logger.info("查询受理到发物流（需要物流的）超期sql:" + sql);
			list = session.findSql(sql);
			acceptToTransfer = (Long) ((Map)list.get(0)).get("total");
			request.setAttribute("acceptToTransfer", acceptToTransfer);
			
			//受理到检测（不需要物流的）
			sql = "SELECT count(*) total FROM cqmass.case_status a , cqmass.case_accept b where a.case_num=b.case_num and b.case_status in ('1','3') and a.case_status='受理' and a.is_transfer=''  " + sqlPiece + "  and TIMESTAMPDIFF(HOUR,a.case_submit_time,sysdate())>24 ";
			logger.info("查询受理到检测（不需要物流的）超期sql:" + sql);
			list = session.findSql(sql);
			acceptToCheck = (Long) ((Map)list.get(0)).get("total");
			request.setAttribute("acceptToCheck", acceptToCheck);
			
			//检测到维修
			sql = "SELECT count(*) total  FROM case_status a, case_check b where a.case_num=b.case_num and a.case_status='检测' and a.is_transfer=''  " + sqlPiece + "  and TIMESTAMPDIFF(HOUR,b.detect_time,sysdate())>24 ";
			logger.info("查询检测到维修超期sql:" + sql);
			list = session.findSql(sql);
			checkToRepair = (Long) ((Map)list.get(0)).get("total");
			request.setAttribute("checkToRepair", checkToRepair);
			
			//物流发起到物流接收
			//TODO a.to_deptId
			sql = "select count(*) total from transfer_list a where a.transfer_state='待接收'  " + (deptId.equals("1")?"":" and a.to_dept_id='" + deptId + "' ") + "  and TIMESTAMPDIFF(HOUR,a.start_date,sysdate())>120";
			logger.info("查询物流发起到物流接收超期sql:" + sql);
			list = session.findSql(sql);
			transferToReceive = (Long) ((Map)list.get(0)).get("total");
			request.setAttribute("transferToReceive", transferToReceive);
			
			//维修完成到发物流
			sql = "select count(*) total from case_status a, case_repair b where a.case_num=b.case_num and a.case_status='维修' and a.case_next_status='返回客户' and b.repair_status='完成维修并物流' and a.is_transfer='需要物流'  " + sqlPiece + "  and TIMESTAMPDIFF(HOUR,b.repair_submit_time,sysdate())>24";
			logger.info("查询维修完成到发物流超期sql:" + sql);
			list = session.findSql(sql);
			doneRepairToTransfer = (Long) ((Map)list.get(0)).get("total");
			request.setAttribute("doneRepairToTransfer", doneRepairToTransfer);
			//TODO
			//维修完成到取机
			sql = "select count(*) total from case_status a, case_repair b where a.case_num=b.case_num and a.case_status='维修' and a.case_next_status='返回客户' and b.repair_status in ('完成维修并物流', '完成维修' )and a.is_transfer=''  " + sqlPiece + "  and TIMESTAMPDIFF(HOUR,b.repair_submit_time,sysdate())>24";
			logger.info("查询维修完成到取机超期sql:" + sql);
			list = session.findSql(sql);
			doneRepairToReturn = (Long) ((Map)list.get(0)).get("total");
			request.setAttribute("doneRepairToReturn", doneRepairToReturn);
			//TODO
			//管理部物流发起到物流接收
			if(deptId.equals("1")){
				sql = "select count(*) total from transfer_list a where a.transfer_state='待接收' and a.to_dept_id=' " + deptId + "'  and TIMESTAMPDIFF(HOUR,a.start_date,sysdate())>120";
				logger.info("查询维修完成到取机超期sql:" + sql);
				list = session.findSql(sql);
				request.setAttribute("doneRepairToReturnManager", (Long) ((Map)list.get(0)).get("total"));
			}
			
			
		} catch (CquptException e) {
			e.printStackTrace();
		}finally{
			if(session != null){
				try {
					session.closeSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return SUCCESS;
	
	}

}
