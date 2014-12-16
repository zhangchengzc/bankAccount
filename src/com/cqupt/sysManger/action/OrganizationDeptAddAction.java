package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import com.cqupt.pub.util.Md;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class OrganizationDeptAddAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6941574699319426537L;

	Logger logger = Logger.getLogger(getClass());

	private Md md5fun = new Md();
		HttpServletRequest request=null;
		private InputStream inputStream;
		public InputStream getInputStream() {
			return inputStream;
		}
		public String execute()
		{
						
logger.info("OrganizationDeptAddAction:");
			this.request = ServletActionContext.getRequest();
			String deptName = request.getParameter("deptName");
			String deptType = request.getParameter("deptType");
		//	beginCredit = request.getParameter("beginCredit");					
		//	accountPic = request.getParameter("accountPic");
		//	accountUser = request.getParameter("accountUser");
		//	accountDate = request.getParameter("accountDate");
			String isUseable = request.getParameter("isUseable");
			String deptPhone = request.getParameter("deptPhone");
			String address = request.getParameter("address");
			String remark = request.getParameter("remark");
			String userName = request.getParameter("userName");
			String userId = request.getParameter("userId");
			String userPsw = request.getParameter("userPsw");
			String companyName = request.getParameter("companyName");
			String transferUserName = request.getParameter("transferUserName");
			String postNum = request.getParameter("postNum");
			String transferUserPhone = request.getParameter("transferUserPhone");
			String saleCentre = request.getParameter("saleCentre");
			try {
				if(request.getParameter("deptName")!=null){			
					if(deptName != null){
						deptName = java.net.URLDecoder.decode(deptName, "UTF-8");
					}else	deptName = "";
					if(deptType != null){
						deptType = java.net.URLDecoder.decode(deptType, "UTF-8");
					}else	deptType = "";
					
					/*if(beginCredit != null){
						beginCredit = java.net.URLDecoder.decode(beginCredit, "UTF-8").toUpperCase();
					}else beginCredit = "";	
					if(accountPic != null){
						accountPic = java.net.URLDecoder.decode(accountPic, "UTF-8");
					}else accountPic = "";
					if(accountUser != null){
						accountUser = java.net.URLDecoder.decode(accountUser, "UTF-8");
					}else accountUser = "";
					if(accountDate != null){
						accountDate = java.net.URLDecoder.decode(accountDate, "UTF-8");
					}else accountDate = "";
					*/
					if(isUseable != null){
						isUseable = java.net.URLDecoder.decode(isUseable, "UTF-8");
					}else isUseable = "";
					if(deptPhone != null){
						deptPhone = java.net.URLDecoder.decode(deptPhone, "UTF-8");
					}else deptPhone = "";
					if(address != null){
						address = java.net.URLDecoder.decode(address, "UTF-8");
					}else address = "";
					if(remark != null){
						remark = java.net.URLDecoder.decode(remark, "UTF-8");
					}else remark = "";
					if(userName != null){
						userName = java.net.URLDecoder.decode(userName, "UTF-8");
					}else userName = "";
					if(userId != null){
						userId = java.net.URLDecoder.decode(userId, "UTF-8");
					}else userId = "";
					if(userPsw != null){
						userPsw = java.net.URLDecoder.decode(userPsw, "UTF-8");
					}else userPsw = "";
					if(companyName != null){
						companyName = java.net.URLDecoder.decode(companyName, "UTF-8");
					}else companyName = "";
					if(transferUserName != null){
						transferUserName = java.net.URLDecoder.decode(transferUserName, "UTF-8");
					}else transferUserName = "";
					if(postNum != null){
						postNum = java.net.URLDecoder.decode(postNum, "UTF-8");
					}else postNum = "";
					if(transferUserPhone != null){
						transferUserPhone = java.net.URLDecoder.decode(transferUserPhone, "UTF-8");
					}else transferUserPhone = "";
					if(saleCentre != null){
						saleCentre = java.net.URLDecoder.decode(saleCentre, "UTF-8");
					}else saleCentre = "";
					
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			HttpServletResponse response=ServletActionContext.getResponse();
	
	           response.setCharacterEncoding("UTF-8");    
	           PrintWriter out;
			try {
				out = response.getWriter();
				out.print(inserDeptinfo(deptName , deptType ,isUseable , deptPhone ,address , remark , userName , userId , userPsw ,companyName,transferUserName,postNum,transferUserPhone,saleCentre));    
		        out.flush();    
		        out.close();  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
	       return null;
		}
		
		private String inserDeptinfo(String deptName , String deptType ,String isUseable , String deptPhone ,String address ,String  remark ,String userName ,
				String  userId ,String  userPsw ,String companyName,String transferUserName,String postNum,String transferUserPhone,String saleCentre) {
			DataStormSession session = null;
			String resultStr = "";
			int bid;
			String sql = "";
			String resultId,sid,parentDeptId;
			List resultListCode = null;
			Map resultMapCode = null;
			try 
			{
				
				logger.info("部门类型:"+deptType);
				if(deptType.equals("维修中心")){
					parentDeptId = "1";
				}else{
					parentDeptId = "101";
				}
					session = DataStormSession.getInstance();
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
					
 //判断店长ID是否存在
						sql = "select user_id from cqmass.sys_user  where user_id='"+userId+"'";
						logger.info("判断店长ID是否存在:"+sql);
						List resultListCode2 = session.findSql(sql);
						//判断部门名称是否存在
						sql = "select dept_name from cqmass.sys_dept  where dept_name='"+deptName+"'";
						logger.info("判断部门名称是否存在:"+sql);
						List resultListCode3 = session.findSql(sql);
						if(resultListCode2.size()==0 && resultListCode3.size()== 0){
							sql = "INSERT INTO cqmass.sys_dept (dept_id,dept_name,parent_dept_id,dept_address,phone_num,email,dept_state,remark,company_name,transfer_user_name,transfer_user_phone,post_num,in_date,sale_centre,dept_type)" +
									"VALUES ('"+resultId+"','"+deptName+"','"+parentDeptId+"','"+address+"','"+deptPhone+"',null,'"+isUseable+"','"+remark+"','"+companyName+"','"+transferUserName+"','"+transferUserPhone+"','"+postNum+"',sysdate(),'"+saleCentre+"','"+deptType+"')";
							logger.info("增加部门到sys_dept表:"+sql);	
							session.add(sql);
						
							sql = "INSERT INTO cqmass.credit (dept_id,dept_name,total_credit,available_credit,used_credit,change_old_credit,change_new_credit,contract_credit) VALUES ('"+resultId+"','"+deptName+"','0','0','0','0','0','0')";
							logger.info("插入额度信息:"+sql);
							session.add(sql);
							
//							sql = "INSERT INTO cqmass.credit_detail (dept_id,dept_name,change_credit,in_date,oper_user_name,account_pic,account_user,account_date) VALUES ('"+resultId+"','"+deptName+"','"+beginCredit+"',sysdate(),'"+userName+"','"+accountPic+"','"+accountUser+"','"+accountDate+"')";
//							session.add(sql);
//logger.info("记录额度详细信息:"+sql);							
							sql = "INSERT INTO cqmass.sys_user  (user_id,user_name,user_pwd,dept_id,dept_name,group_id,group_name,user_state,user_email,phone_num,remark,in_date) VALUES ('"+userId+"','"+userName+"','"+md5fun.getMD5ofStr(userPsw)+"','"+resultId+"','"+deptName+"','121','网点经理','"+isUseable+"',null,'"+deptPhone+"','"+remark+"',sysdate())";	
							logger.info("添加用户到sys_user表:"+sql);
							session.add(sql);
							
							resultStr = "success";
						}else{
							if(resultListCode3.size()!=0){
								resultStr="deptExist";
							}else{
								resultStr="idExist";
							}
						}
						session.closeSession();
				
				
			}catch (Exception e) {
			
				try {
					resultStr = "error";
					session.exceptionCloseSession();
				} catch (CquptException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
		
			
			}
logger.info("resultStr1 ="+resultStr);			
			return resultStr;

		}
		
}
