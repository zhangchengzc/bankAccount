package com.cqupt.pub.util;

import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class ExcelIn1 {
	
	public static String getListByJxl(String fileFullPath, int startRow,
			int startColumn, int numCol) {
// numCol是Excel规定的列数
		StringBuffer agentCommissionResult = new StringBuffer();
		String content = null;
		 
		int count;//记录每一行元素实际个数
		jxl.Workbook book = null;
		try {
			InputStream is = new FileInputStream(fileFullPath);
			WorkbookSettings setting = new WorkbookSettings();
			java.util.Locale locale = new java.util.Locale("zh", "CN");
			setting.setLocale(locale);
			setting.setEncoding("ISO-8859-1");
			book = Workbook.getWorkbook(is, setting);// 获得第一个工作表对象

			for (int i = 0; i < book.getNumberOfSheets(); i++) {
				Sheet sheet = book.getSheet(i);				
				int sheetRows = sheet.getRows();

				for (int j = startRow; j < sheetRows; j++) {
					Cell[] rowContents = sheet.getRow(j);// 存放每一行数据
					count = 0;//遍历每一行时，count初始化为0
					//System.out.println("每一行数据长度：" + rowContents.length);
					
					if (rowContents.length != numCol) {
						agentCommissionResult.replace(0, agentCommissionResult.length(), " ");
						break;
					} else {
						for (int k = startColumn; k < rowContents.length; k++) {
							
							//System.out.println(!rowContents[k].getContents().equals(""));//每个元素是否为空
							//System.out.println(rowContents[k].getContents().indexOf(" ") != 0);//元素是否有空格
							if (!rowContents[k].getContents().equals("")
									&& rowContents[k].getContents().indexOf(" ") != 0) {
								content = rowContents[k].getContents().trim();
								agentCommissionResult.append(content);
								if (k != rowContents.length - 1)
									agentCommissionResult.append("@");

								count++;// 记录每一行的元素个数

							}
						

						}

						agentCommissionResult.append(";");
						
						//System.out.println("这一行的元素个数："+count);
						if (count != numCol) {
							agentCommissionResult.replace(0,agentCommissionResult.length(), " ");
							break;
						}
					}
				}

			}
		} catch (Exception e) {

			System.out.println("问题在JxlExcelUtil.java类里边getListByJxl方法里边");
			e.printStackTrace();
		} finally {
			book.close();
		}
		System.out.println(agentCommissionResult);
		return agentCommissionResult.toString();
	}
}
