package com.cqupt.pub.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelOut1 {

	/**
	 * 外部调用接口，传入包含路径的Excel名称和SQL，会在指定路径下生成Excel
	 */
	
	public static WritableWorkbook exportToExcel(String name,
			List<List<List<String>>> splitList) {
	
		Map resultMap = null;
		StringBuffer strList = new StringBuffer();
		String[] rowArray = null;
		String[] colArray = null;
		String targetfile = name;//输出的excel文件名,带路径
		String worksheet = "Sheet1";//输出的excel文件工作表名
		String[] title = {"商品类型","商品品牌","商品型号","库存数量","渠道名称"};//excel工作表的标题
		jxl.write.WritableWorkbook workbook = null; 
		
		try 
		{ //创建可写入的Excel工作薄,运行生成的文件在tomcat/bin下  
			//OutputStream os=new FileOutputStream(targetfile);    
			File file = new File(name);
			if (file.exists()) {
				file.delete();
			}
			workbook=Workbook.createWorkbook(file);
			jxl.write.WritableSheet sheet = workbook.createSheet(worksheet, 0); //添加第一个工作表
			jxl.write.Label label;   
			for (int i=0; i<title.length; i++){   
			  
				//Label(列号,行号 ,内容 )   
				label = new jxl.write.Label(i, 0, title[i]); //定义单元格对象，指明位置和内容  
				sheet.addCell(label);    //将定义好的单元格添加到工作表中
			
			}  
			//把splitList放入StringBuffer strList，一行用‘；’号隔开
			if(splitList.size() == 0) {
				strList.append("商品缺货"+",");
				strList.append("商品缺货"+",");
				strList.append("商品缺货"+",");
				strList.append("商品缺货"+",");
				strList.append("商品缺货"+",");
			} else {
				for (int j = 0; j < splitList.size(); j++) {
					resultMap = (Map) splitList.get(j);
					strList.append(resultMap.get("categoryName").toString()+",");
					strList.append(resultMap.get("brandName").toString()+",");
					strList.append(resultMap.get("prodVersion").toString()+",");
					strList.append(resultMap.get("tracNum").toString()+",");
					
					strList.append(resultMap.get("hostDeptName").toString());
					if(j < splitList.size()-1)
						strList.append(";");
				}
	//System.out.println(strList.toString());	
						}
			rowArray = strList.toString().split(";");
			
			for (int j = 0; j < title.length; j++) {//列
				
				
				for(int i=1; i <= rowArray.length; i++){	// 行,数据从第一行开始存			
//System.out.println(rowArray[i-1].toString());					
					colArray = rowArray[i-1].split(",");
					
					label = new jxl.write.Label(j, i, colArray[j].toString());	
//System.out.println(colArray[j].toString());
					sheet.addCell(label);
				}
			}

			
			workbook.write();//写入Excel工作表
			workbook.close();//关闭Excel工作薄对象
		} catch (RowsExceededException e) {

			e.printStackTrace();
		} catch (WriteException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		//System.out.println(workbook);
		return workbook;
	}
}
	
			
			