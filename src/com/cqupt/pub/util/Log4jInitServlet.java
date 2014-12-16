package com.cqupt.pub.util;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInitServlet extends HttpServlet{
	/**
	 * log4j日志配置
	 */
	private static final long serialVersionUID = 1L;

	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException{
		  
	}
	
    public void init() throws ServletException {   
	        String prefix = getServletContext().getRealPath("/");
	        String test = getServletContext().getRealPath("");
	        System.setProperty("cqmass", test);
	        String file = getServletConfig().getInitParameter("configfile");
System.out.println("prefix+file:"+prefix+file);
	        PropertyConfigurator.configure(prefix+file);   
	    }   
    
	

}
