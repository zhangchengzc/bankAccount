package com.cqupt.sysManger.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class SysScanImgUploadAction extends ActionSupport{

		private static final long serialVersionUID = -6941574699319426537L;
		HttpServletRequest request = null;
		private static final int BUFFER_SIZE = 20 * 1024; // 20K

		private File myFileHead; // 与页面 <input type="file"> 控件的 name 保持一致

		private String myFileHeadFileName; 
		private String contentType;
		
		public File getMyFileHead() {
			return myFileHead;
		}

		public void setMyFileHead(File myFileHead) {
			this.myFileHead = myFileHead;
		}

		public String getContentType() {
			return contentType;
		}

		public String getmyFileHeadFileName() {
			
			return myFileHeadFileName;
		}

		public void setmyFileHeadFileName(String myFileHeadFileName) {
			
			this.myFileHeadFileName = myFileHeadFileName;
		}

		// 同上
		public void setMyFileContentType(String contentType) {
			this.contentType = contentType;
		}
		
			public String execute() throws Exception {
				
				this.request = ServletActionContext.getRequest();
				HttpServletResponse response=ServletActionContext.getResponse();
				response.setCharacterEncoding("UTF-8");   //设置字符集
				
				String nowTimeStr = ""; // 保存当前时间
				SimpleDateFormat sDateFormat;
				Random r = new Random(); // 一个随机数对象
				String inId=null;
				// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
				int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
				sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
				nowTimeStr = sDateFormat.format(new Date()); // 当前时间	
				inId = nowTimeStr + rannum;
	        	String extName = ""; // 保存文件拓展名
	    		String newFileName = ""; // 保存新的文件名
	    		String savePath = ServletActionContext.getServletContext().getRealPath(""); // 获取项目根路径
	    		savePath = savePath + "/upload/creditCertificate"; // 拼串组成要上传保存文件的路径，即：D:\Program
	    	
	    		String fileName = this.getmyFileHeadFileName();
	    		System.out.println("1111"+fileName);
	    		fileName = java.net.URLDecoder.decode(fileName, "UTF-8");
	    		System.out.println("2222"+fileName);
	    		extName = fileName.substring(fileName.lastIndexOf(".")); 
		    	newFileName = inId+extName;
System.out.println("扫描件图片的保存路径："+savePath + "/"+newFileName);			
	    		myFileHead.renameTo(new File(savePath + "/"+newFileName)); // 保存文件		    		
	    		 PrintWriter out;
	    			try {
	    				out = response.getWriter();	
	    				String resultStr = "success@"+inId+"@"+newFileName;
	    				out.print(resultStr);
	    				out.flush();
	    				out.close();
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			}
	    			return null; // 这里不需要页面转向，所以返回空就可以了
			}

}
