package com.cqupt.pub.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class ExcelParse {

	public static String getListByJxl(String fileFullPath, int startRow,
			int startColumn, int numCol) {
		// numCol是Excel规定的列数
		StringBuffer strResult = new StringBuffer();
		String content = null;

		int count;// 记录每一行元素实际个数
		InputStream is = null;
		jxl.Workbook book = null;
		try {
			is = new FileInputStream(fileFullPath);
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
					count = 0;// 遍历每一行时，count初始化为0
					//System.out.println("每一行数据长度：" + rowContents.length);

					for (int k = startColumn; k < numCol-1; k++) {// 最后一列以前的要判断是否为空或是否有空格

						 //System.out.println(!rowContents[k].getContents().equals(""));//每个元素是否为空
						// System.out.println(rowContents[k].getContents().indexOf(" ") != 0);//元素是否有空格
						if (!rowContents[k].getContents().equals("")&& rowContents[k].getContents().indexOf(" ") != 0) {
							content = rowContents[k].getContents().trim();
							strResult.append(content);
							//if (k != rowContents.length - 1)
							strResult.append("&");//单元格间用&分开

							count++;// 记录每一行的元素个数

						} else {
							strResult.replace(0, strResult.length(), " ");
							break;
						}

					}
//对最后一列（）单独处理，允许为空，如果为空填充一个空格
					if (rowContents.length == numCol - 1) {
					
						strResult.append("无");
						count++;
					}else{
						content = rowContents[numCol-1].getContents().trim();
						if(content.equals("")){
							strResult.append("无");
						}else{
							strResult.append(content);
						}
						count++;
					}

					strResult.append("@");//行间用@分开

					// System.out.println("这一行的元素个数："+count);
					if (count != numCol) {
						strResult.replace(0, strResult.length(), " ");
						break;
					}

				}

			}
		} catch (Exception e) {

			System.out.println("问题在JxlExcelUtil.java类里边getListByJxl方法里边");
			e.printStackTrace();
		} finally {
			book.close();
			try {
				is.close();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}
		System.out.println(strResult);
		return strResult.toString();
	}
}
