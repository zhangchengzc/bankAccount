package com.cqupt.pub.util;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CharsetEncodingFilter implements Filter{
	/**
	 * 字符集过滤器处理
	 * 解决中文乱码的问题
	 */
	private String encoding = "";  
	
	public void destroy(){}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse  response = (HttpServletResponse)servletResponse;
			request.setCharacterEncoding(encoding);
			filterChain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.encoding = filterConfig.getInitParameter("encoding");	
	}


}
