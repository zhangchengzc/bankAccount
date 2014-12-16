package com.cqupt.pub.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Tools {
	
	public static String getOrderID(){
		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		Random r = new Random();
		int rannum = (int) (r.nextDouble() * (99 - 10 + 1)) + 10;   // 获取随机数
		String nowTimeStr = getNowTime("yyyyMMddHHmmss");// 时间格式化的格式
			return nowTimeStr + rannum;
	}
	
	public static String getNowTime(String format){
		// 当前时间
		SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
		return sDateFormat.format(new Date()); 	
	}
	public static String getNowDayOfTheMonth(){
		//当前月当日
		Calendar curCal = Calendar.getInstance();
        SimpleDateFormat datef =  new SimpleDateFormat("yyyy-MM-dd");
       // curCal.set(Calendar.DAY_OF_MONTH);
       // Date beginTime = curCal.getTime();
      //String sTime = datef.format(beginTime);
        Date currentTime = new Date();
        String sTime = datef.format(currentTime);
        return sTime;
	}
	public static String getFirstDayOfTheMonth(){
		//当前月的第一天
		Calendar curCal = Calendar.getInstance();
        SimpleDateFormat datef =  new SimpleDateFormat("yyyy-MM-dd");
        curCal.set(Calendar.DAY_OF_MONTH, 1);
        Date beginTime = curCal.getTime();
        String sTime = datef.format(beginTime);
        return sTime;
	}
	public static String getLastDayOfTheMonth(){	
		 //当前月的最后一天   
        Calendar cal = Calendar.getInstance();
		SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");		            
		cal.set( Calendar.DATE, 1 );
		cal.roll(Calendar.DATE, - 1 );
		Date endTime=cal.getTime();
		String sTime = datef.format(endTime);
		return sTime;
	}
	public static String getFirstDayOfNextMonth(){
		//下一个月的第一天
		Calendar curCal = Calendar.getInstance();
        SimpleDateFormat datef =  new SimpleDateFormat("yyyy-MM-dd");
        curCal.set(Calendar.MONTH, curCal.get(Calendar.MONTH)+1);
        Date beginTime = curCal.getTime();
        String sTime = datef.format(beginTime);
        return sTime;
	}
	
	public static String getWorkFinanceCountDetail(String txtYear,String txtMonthId){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = sDateFormat.format(new Date()); // 当前时间	
		//int txtMonthId1 = Integer.parseInt(txtMonthId)+1;
		DecimalFormat df = new DecimalFormat("00");
		String txtMonthId2 = df.format(Integer.parseInt(txtMonthId)+1);
		if(nowDate.substring(0,4).equals(txtYear) && (nowDate.substring(5, 7).equals(txtMonthId2) || nowDate.substring(5, 7).equals(txtMonthId))){
			return "cqmass.work_finance_count_detail";
		}else{
			return "cqmass.work_finance_count_detail_"+txtYear+txtMonthId;
		}
		
        
	}
}
