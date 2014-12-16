package com.cqupt.pub.exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;

public class CquptException extends Exception {
	
	private static final long serialVersionUID = 1L;  //此处不怎么懂？
	private String exMsg;
	private int exCode;
	
	public CquptException(String exMsg,int exCode){
		
		super(exMsg);
		this.exCode = exCode;
		this.setExceptionMsg(exCode);
						
	}
	
	public CquptException(Exception ex){
		
		super(ex);
		this.setExceptionMsg(ex);
	}
	
	private void setExceptionMsg(Exception ex){
		
		this.exCode = this.getExceptionCode(ex);
		this.setExceptionMsg(this.exCode);
	}
	
	private void setExceptionMsg(int exCode){
		switch(exCode){
		case CquptExceptionCode.DBEXCODE:
			this.exMsg = CquptExceptionCode.DBEXMSG;
			break;
		case CquptExceptionCode.WEBEXCODE:
			this.exMsg = CquptExceptionCode.WEBEXMSG;
			break;
		case CquptExceptionCode.EJBEXCODE:
			this.exMsg = CquptExceptionCode.EJBEXMSG;
			break;
		default:
			this.exMsg = CquptExceptionCode.SYSEXMSG;				
		}		
	}
	//retuen custom ExceptionCode
	private int getExceptionCode(Exception ex){
		
		if(ex instanceof CquptException){
			return ((CquptException) ex ).exCode;						
		}else if (ex instanceof SQLException ){
			return CquptExceptionCode.DBEXCODE;			
		}else if (ex instanceof FileNotFoundException 
				  || ex instanceof IOException){
			return CquptExceptionCode.SYSEXCODE;
		}else if (ex instanceof NamingException ){
			return CquptExceptionCode.EJBEXCODE;
		}
		  return CquptExceptionCode.SYSEXCODE;
	}
	public String getExceptionMsg(){
		return this.exMsg;
	}

}















