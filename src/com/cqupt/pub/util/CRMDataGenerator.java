package com.cqupt.pub.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
/**
 * 每天生成前一天的CRM数据，包括“省终端售后服务网点信息上报数据文件 ”和“终端维修记录上报数据文件”
 * @author Administrator
 *
 */
public class CRMDataGenerator extends HttpServlet implements Runnable {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("initial CRMDataGenerator  ...");
		new Thread(new CRMDataGenerator()).start();
		System.out.println("initial CRMDataGenerator finished");
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000 * 60 * 55);
				if(Tools.getNowTime("HH").equals("02")) {
					logger.info("CRMDataGenerator start... " + Tools.getNowTime("yyyy-MM-dd HH:mm:ss"));
					generateCRMFile();
					logger.info("CRMDataGenerator completed... " + Tools.getNowTime("yyyy-MM-dd HH:mm:ss"));
				} else {
					logger.info("CRMDataGenerator abort " + Tools.getNowTime("yyyy-MM-dd HH:mm:ss"));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void generateCRMFile() {
		BufferedWriter deptAdded = null;
		BufferedWriter repairRecord = null;
		String deptAddedFileName = "";
		String repairRecordFileName = "";
		String yesterday = "", today;
		DataStormSession session = null;
		String sql = "";
		List list = null;
		try {
			session = DataStormSession.getInstance();
			today = Tools.getNowTime("yyyyMMddHHmmss");
			yesterday = getPreviousDay();
			deptAddedFileName = "C:\\Users\\Administrator\\Desktop\\CRMSEND_0044_230_" + yesterday + "_001";
			repairRecordFileName = "C:\\Users\\Administrator\\Desktop\\CRMSEND_0046_230_" + yesterday + "_001";
			//TODO
			//sql = "SELECT	'重庆市' AS area,	dept_name,dept_address,	'09:00-18:00' AS work_time,	ifnull(phone_num,'无') as phone_num,IF (parent_dept_id != '1',IF (	parent_dept_id = '00',	'省级维修中心',	'接机点'), '02') as dept_type,'4' as people,'维修业务、换机业务' as dept_function,'华为、中兴、LG、IPHONE' as repair_brand, '无' as remark,'01' as exchange FROM sys_dept WHERE	parent_dept_id != '2' and dept_id != '2' and in_date > '" + getPreviousDayFormally() + " 00:00:00' and in_date < '" + getPreviousDayFormally() + " 23:59:59' ";
			sql = "SELECT a.*,b.people from(SELECT '重庆市' AS area,dept_name,dept_address,'08:00-18:00' AS work_time,ifnull(phone_num, '无') AS phone_num,IF (dept_id = '112','01',IF (LENGTH(dept_id) = 3,'02',	'03')) AS dept_type,if(LENGTH(dept_id)=3,'维修功能、配件销售、自有业务体验、延保等服务产品的销售','接机') AS dept_function,'华为、中兴、LG、IPHONE等所有品牌' AS repair_brand,'无' AS remark,'01' AS exchange FROM sys_dept WHERE parent_dept_id != '2' AND dept_id != '2'  and in_date > '" + getPreviousDayFormally() + " 00:00:00' and in_date < '" + getPreviousDayFormally() + " 23:59:59' ) a LEFT JOIN (SELECT count(*) people,dept_id,dept_name from sys_user GROUP BY dept_id) b on a.dept_name = b.dept_name";
			list = session.findSql(sql);
			System.out.println(sql);
			
			//创建“省终端售后服务网点信息上报数据文件 ” 文件，并写入数据
			deptAdded = new BufferedWriter(new PrintWriter(new File(deptAddedFileName),"GBK"));
			repairRecord = new BufferedWriter(new PrintWriter(new File(repairRecordFileName),"GBK"));
			//“省终端售后服务网点信息上报数据文件 ”写入“文件头”信息
			deptAdded.write("10|");
			deptAdded.write("0044|");
			deptAdded.write("230|");
			deptAdded.write("001|");
			deptAdded.write(today + "|");
			deptAdded.write(yesterday + "000000|");
			deptAdded.write(yesterday + "235959|");
			deptAdded.write("01|");
			deptAdded.write(list.size() + "|");//deptAdded.write(size + "|");
			deptAdded.write("                                          |\n");
			//“省终端售后服务网点信息上报数据文件 ”写入“文件体”信息
			int netNo = 100000000;
			for(int i = 0; i < list.size(); i ++) {
				netNo ++;
				String temp = netNo + "";
				Map map = (Map) list.get(i);
				deptAdded.write("230" + temp.substring(1, 9) + "|");
				deptAdded.write(map.get("area") + "|");
				deptAdded.write(map.get("deptName") + "|");
				deptAdded.write(map.get("deptAddress") + "|");
				deptAdded.write(map.get("workTime") + "|");
				deptAdded.write(map.get("phoneNum") + "|");
				deptAdded.write(map.get("deptType") + "|");
				deptAdded.write(map.get("people") + "|");
				deptAdded.write(map.get("deptFunction") + "|");
				deptAdded.write(map.get("repairBrand") + "|");
				deptAdded.write(map.get("remark") + "|");
				deptAdded.write(map.get("exchange") + "|");
				deptAdded.write("\n");
				
			}
			
			
			//sql = "select IF(b.case_type_detail='保修期外','保外','保内') as case_type,	b.client_name,	b.client_tel,	b.brand_name,	b.version_name,	b.product_num,	b.malfunction_description,	'重庆市' as province,	b.dept_name,	b.oper_user_name,DATE_FORMAT(a.case_submit_time, '%Y%m%d %H:%i') case_submit_time,	DATE_FORMAT(deal_time, '%Y%m%d %H:%i') deal_time,	a.to_time,'3G手机' as net_style,'非集采' as jicai FROM(	SELECT a.case_num,	a.case_submit_time,	b.deal_time,ROUND((	UNIX_TIMESTAMP(b.deal_time) - UNIX_TIMESTAMP(a.case_submit_time)) / (24 * 60 * 60),	2	) AS to_time FROM	case_status a,case_back2user b WHERE	a.case_status = '返回客户' AND a.case_num = b.case_num	AND b.deal_time >= (SELECT DATE_FORMAT(	(	SELECT DATE_SUB(SYSDATE(), INTERVAL 1 DAY)	),'%Y-%m-%d')) && b.deal_time < (SELECT DATE_FORMAT(SYSDATE(), '%Y-%m-%d'))	) a,case_accept b WHERE	a.case_num = b.case_num limit 5";
			sql = "SELECT IF (b.case_type_detail = '保修期外','保外',	'保内') AS case_type, b.client_name, b.client_tel, b.brand_name, b.version_name, b.product_num, b.malfunction_description, b.sub_company AS province, b.dept_id, b.oper_user_name,DATE_FORMAT(a.case_submit_time,'%Y%m%d %H:%i') case_submit_time, DATE_FORMAT(deal_time, '%Y%m%d %H:%i') deal_time,a.to_time,'2G/3G手机' AS net_style,'非集采' AS jicai FROM(	SELECT a.case_num,a.case_submit_time,	b.deal_time,ROUND((	UNIX_TIMESTAMP(b.deal_time) - UNIX_TIMESTAMP(a.case_submit_time)) / (24 * 60 * 60),2) AS to_time FROM case_status a,case_back2user b WHERE	a.case_status = '返回客户'AND a.case_num = b.case_num 	AND b.deal_time >= (SELECT DATE_FORMAT((SELECT DATE_SUB(SYSDATE(), INTERVAL 1 DAY)	),'%Y-%m-%d')) && b.deal_time < (SELECT DATE_FORMAT(SYSDATE(), '%Y-%m-%d'))) a,case_accept b WHERE	a.case_num = b.case_num";
			list = session.findSql(sql);
			System.out.println(sql);
			//“终端维修记录上报数据文件 ”写入“文件头”信息
			repairRecord.write("10|");
			repairRecord.write("0046|");
			repairRecord.write("230|");
			repairRecord.write("001|");
			repairRecord.write(today + "|");
			repairRecord.write(yesterday + "000000|");
			repairRecord.write(yesterday + "235959|");
			repairRecord.write("01|");
			repairRecord.write(list.size() + "|");//deptAdded.write(size + "|");
			repairRecord.write("                                          |\n");
		
			
			
			//“终端维修记录上报数据文件 ”写入“文件体”信息
			netNo = 1000000;
			for(int i = 0; i < list.size(); i ++) {
				netNo ++;
				String temp = netNo + "";
				Map map = (Map) list.get(i);
				repairRecord.write("230" + yesterday + temp.substring(1, 7) + "|");
				repairRecord.write(map.get("caseType") + "|");
				repairRecord.write(map.get("clientName") + "|");
				repairRecord.write(map.get("clientTel") + "|");
				repairRecord.write(map.get("brandName") + "|");
				repairRecord.write(map.get("versionName") + "|");
				repairRecord.write(map.get("productNum") + "|");
				repairRecord.write(map.get("malfunctionDescription").toString().replaceAll("\n", "") + "|");
				repairRecord.write(map.get("province") + "|");
				repairRecord.write(map.get("deptId") + "|");
				repairRecord.write(map.get("operUserName") + "|");
				repairRecord.write(map.get("caseSubmitTime") + "|");
				repairRecord.write(map.get("dealTime") + "|");
				repairRecord.write(map.get("toTime") + "|");
				repairRecord.write(map.get("netStyle") + "|");
				repairRecord.write(map.get("jicai") + "|");
				repairRecord.write("\n");
				
			}
			
			deptAdded.flush();
			repairRecord.flush();
			session.closeSession();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				e1.printStackTrace();
			}
		} catch (CquptException e) {
			e.printStackTrace();
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				e1.printStackTrace();
			}
		} finally {
			if(deptAdded != null) {
				try {
					deptAdded.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(repairRecord != null) {
				try {
					repairRecord.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	
	public static void main(String args[]) {
		//System.out.println("                                          ".length());
		String s = "line one\nline two\nline three";
		System.out.println("source:" + s);
		s = s.replaceAll("\n", "");
		System.out.println("after:" + s);
		new CRMDataGenerator().generateCRMFile();
		//System.out.println(new CRMDataGenerator().getPreviousDay());
	}
	
	private String getPreviousDay() {
		Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, -1);//得到前一天
        return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}
	
	private String getPreviousDayFormally() {
		Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, -1);//得到前一天
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

}





