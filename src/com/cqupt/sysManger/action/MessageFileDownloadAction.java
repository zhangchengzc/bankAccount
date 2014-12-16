package com.cqupt.sysManger.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import common.Logger;

public class MessageFileDownloadAction  extends ActionSupport{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5489994619108462686L;
	/**
	 *   采购入库单录入文件模板
	 */
Logger logger = Logger.getLogger(this.getClass());
	HttpServletRequest request = null;

	public String execute() {
logger.info("MessageFileDownloadAction:");
		request = ServletActionContext.getRequest();
		String appendixName =  request.getParameter("appendixName");
		request = ServletActionContext.getRequest();     
		String savePath = ServletActionContext.getServletContext().getRealPath(""); // 获取项目根路径
		String Path = savePath + "/upload/creditCertificate/"+appendixName;
System.out.println(Path);
  
   	    request.setAttribute("path", Path);
   	    request.setAttribute("userFileName", appendixName);
		return "download";
	
		
		
	}
}