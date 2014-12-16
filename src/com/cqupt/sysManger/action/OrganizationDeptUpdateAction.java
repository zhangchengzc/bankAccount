package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class OrganizationDeptUpdateAction extends ActionSupport{

   	Logger logger = Logger.getLogger(getClass());

		private static final long serialVersionUID = 1794195924380050122L;
		HttpServletRequest request=null;

		private InputStream inputStream;
		public InputStream getInputStream() {
			return inputStream;
		}
		
		public String execute()
		{
			logger.info("OrganizationDeptUpdateAction:");
			this.request = ServletActionContext.getRequest();

			String deptName,initialDeptName;
			try {
				initialDeptName = request.getParameter("initialDeptName") ;
				deptName = request.getParameter("deptName");
				String initialDeptType = request.getParameter("initialDeptType") ;
				String deptType = request.getParameter("deptType") ;
				String isUseable = request.getParameter("isUseable") ;
				String deptPhone = request.getParameter("deptPhone") ;
				String address = request.getParameter("address") ;
				String remark = request.getParameter("remark") ;
				String deptId = request.getParameter("hidDeptId") ;
				String companyName  = request.getParameter("companyName") ;
				String transferUserName  = request.getParameter("transferUserName") ;
				String postNum  = request.getParameter("postNum") ;
				String transferUserPhone = request.getParameter("transferUserPhone") ;
				//String totalCredit = java.net.URLDecoder.decode(request.getParameter("totalCredit") , "UTF-8");
				//String availableCredit = java.net.URLDecoder.decode(request.getParameter("availableCredit") , "UTF-8");
				try{
					if(initialDeptName != null){
						initialDeptName = java.net.URLDecoder.decode(initialDeptName, "UTF-8");
					}else	initialDeptName = "";
					if(deptName != null){
						deptName = java.net.URLDecoder.decode(deptName, "UTF-8");
					}else	deptName = "";
					if(initialDeptType != null){
						initialDeptType = java.net.URLDecoder.decode(initialDeptType, "UTF-8");
					}else	initialDeptType = "";
					if(deptType != null){
						deptType = java.net.URLDecoder.decode(deptType, "UTF-8");
					}else deptType = "";
					if(isUseable != null){
						isUseable = java.net.URLDecoder.decode(isUseable, "UTF-8");
					}else isUseable = "";	
					if(address != null){
						address = java.net.URLDecoder.decode(address, "UTF-8");
					}else address = "";
					if(remark != null){
						remark = java.net.URLDecoder.decode(remark, "UTF-8");
					}else remark = "";
					
					if(companyName != null){
						companyName = java.net.URLDecoder.decode(companyName, "UTF-8");
					}else companyName = "";
					if(transferUserName != null){
						transferUserName = java.net.URLDecoder.decode(transferUserName, "UTF-8");
					}else transferUserName = "";
					
				}catch(UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				HttpServletResponse response=ServletActionContext.getResponse();
		        response.setCharacterEncoding("utf-8");    
		        PrintWriter out;
		        out = response.getWriter();
		        out.print(updateDeptinfo(transferUserPhone,deptName, initialDeptName,initialDeptType,deptType, isUseable, deptPhone, address, remark, deptId, companyName,transferUserName,postNum));    
			    out.flush();    
			    out.close();  
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	       return null;


		}
		
		private String updateDeptinfo(String transferUserPhone,String deptName, String initialDeptName,String initialDeptType, String deptType,
				String isUseable,String deptPhone,String address,String remark,String deptId,String companyName,String transferUserName,String postNum) {
			DataStormSession session = null;
			String resultStr = "";
			String sql = "";
			String parentDeptId="",resultId="",sid="",groupId="",groupName="";
			int bid;
			try 
			{
				session = DataStormSession.getInstance();
logger.info("initialDeptName:"+initialDeptName+"deptName:"+deptName);
logger.info("initialDeptType:"+initialDeptType+"deptType:"+deptType);
			if(!initialDeptName.equals(deptName) || !initialDeptType.equals(deptType)){  
				//判断部门名称是否作过修改，若修改后需判断修改过部门名称是否已经被使用					
				//部门名称改了，或网点类型改了。

				sql = "select dept_name from cqmass.sys_dept  where dept_name='"+deptName+"'";
logger.info("判断部门名称是否已被使用:"+sql);
				List resultListCode = session.findSql(sql);
				Map resultMapCode = null;
				if(resultListCode.size()==0){
					//部门名称没被被使用
					//网点类型改了
					if(!initialDeptType.equals(deptType)){
						//新生成部门编号
						if(deptType.equals("维修中心")){
							parentDeptId = "1";
							groupId="121";
							groupName="网点经理";
						}else{
							parentDeptId = "101";
							groupId="131";
							groupName="接机点经理";
						}
						//生成部门id
						sql = "SELECT count(dept_id) id FROM cqmass.sys_dept where parent_dept_id='"+ parentDeptId + "'";
						logger.info("查询该部门下是否有子部门:"+sql);
						resultListCode = session.findSql(sql);
						
						resultMapCode = (Map) resultListCode.get(0);
						String mark = resultMapCode.get("id").toString();
						
						if(mark.equals("0")){
							resultId = parentDeptId+"01";
						}else{
							sql = "SELECT max(dept_id) id FROM cqmass.sys_dept where parent_dept_id = '"+ parentDeptId +"'";
							logger.info("查询本级中最大的部门号:"+sql);
							resultListCode = session.findSql(sql);
					
							resultMapCode = (Map) resultListCode.get(0);
							resultId = resultMapCode.get("id").toString();
		
			                if(parentDeptId.length()== 1){ //判断父部门的位数，若为一位，从第二位开始取，若为三位 ，从第四位开始取；
			                	sid = resultId.substring(1); 
			                }else{
			                	sid = resultId.substring(3);
				             }
			                logger.info("sid ="+sid);
							 bid = Integer.parseInt(sid) + 1;
							 DecimalFormat df = new DecimalFormat("00");
							 resultId = parentDeptId + df.format(bid);
							 logger.info("resultId = "+resultId);
							/*if (bid < 100) {
								if (bid < 10)
									resultId = parentDeptId + "0" + bid;
								else 
									resultId = parentDeptId + bid;
							}*/
						}  
						sql = "update sys_dept set dept_id='"+resultId+"',dept_name='"+deptName+"',dept_address='"+address+"',phone_num='"+deptPhone+"',transfer_user_name='"+transferUserName+"',post_num='"+postNum+"'," +
								"dept_state='"+isUseable+"',parent_dept_id='"+parentDeptId+"',remark='"+remark+"',company_name='"+companyName+"',transfer_user_phone='"+transferUserPhone+"',dept_type='"+deptType+"'" +
										"  where dept_id = '"+deptId+"'";
						logger.info("更新部门表1："+sql);
						session.update(sql);
					}else{
						
						resultId = deptId;
						sql = "update sys_dept set dept_id='"+resultId+"',dept_name='"+deptName+"',dept_address='"+address+"',phone_num='"+deptPhone+"',transfer_user_name='"+transferUserName+"',post_num='"+postNum+"'," +
								"dept_state='"+isUseable+"',remark='"+remark+"',company_name='"+companyName+"',transfer_user_phone='"+transferUserPhone+"'" +
										"  where dept_id = '"+deptId+"'";
						logger.info("更新部门表2："+sql);
						session.update(sql);
					}
					//改其它表的部门名称和ID；
					sql = "update cqmass.allocate set from_dept_id='"+resultId+"',from_dept_name='"+deptName+"' where from_dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.allocate set to_dept_id='"+resultId+"',to_dept_name='"+deptName+"' where to_dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.back_supplier_fault set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.case_accept set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.case_back2user set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.case_check set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.case_repair set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.case_status set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					
					sql = "update cqmass.case_gadgets_old_back set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.case_gadgets_use set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.case_gadgets_use_back set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					
					sql = "update cqmass.credit set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.credit_change_detail set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.credit_detail set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.gadgets_out_factory set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.gadgets_return_factory set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					
					sql = "update cqmass.gadgets_storage_in set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.message_read_detail set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.mobile_box_mag set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.mobile_mag set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.mobile_return_factory set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					
					sql = "update cqmass.order_apply_detail set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.order_apply_list set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.order_list set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.product_change_room set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.sale_back set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					
					sql = "update cqmass.sale_company_list set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.sale_order set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.sale_tab_temp set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.sys_dept set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.sys_user set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					if(!initialDeptType.equals(deptType)){
						//部门类型更改时，更改用户组
						sql = "update cqmass.sys_user set group_id='"+groupId+"',group_name='"+groupName+"' where dept_id = '"+deptId+"'";
						logger.info("更新部门："+sql);
						session.update(sql);
					}
					sql = "update cqmass.transfer_list set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.transfer_list set to_dept_id='"+resultId+"',to_dept_name='"+deptName+"' where to_dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.user_repair_brand set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					sql = "update cqmass.work_finance_count set dept_id='"+resultId+"',dept_name='"+deptName+"' where dept_id = '"+deptId+"'";
					logger.info("更新部门："+sql);
					session.update(sql);
					
					//sql = "update credit set dept_name='"+deptName+"',total_credit='"+totalCredit+"',available_credit='"+availableCredit+"' where dept_id = '"+deptId+"'";
					//session.update(sql);

					resultStr = "success";				
				
				}else{
					resultStr = "deptNameError";
				}
			}else{
				//部门名称没改
					sql = "update sys_dept set dept_name='"+deptName+"',dept_address='"+address+"',phone_num='"+deptPhone+"',transfer_user_name='"+transferUserName+"',post_num='"+postNum+"'," +
					"dept_state='"+isUseable+"',remark='"+remark+"',company_name='"+companyName+"',transfer_user_phone='"+transferUserPhone+"'" +
							"  where dept_id = '"+deptId+"'";
	logger.info("更新部门表3："+sql);
					session.update(sql);
				//sql = "update credit set dept_name='"+deptName+"',total_credit='"+totalCredit+"',available_credit='"+availableCredit+"' where dept_id = '"+deptId+"'";
				//session.update(sql);

				resultStr = "success";
			}
				session.closeSession();
			
			}catch (Exception e) 
			{
				resultStr = "error";
				try {
					session.exceptionCloseSession();
				} catch (CquptException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				
			}
			logger.info("resultStr:"+resultStr);	
			return resultStr;

		}
		
		
		
}
