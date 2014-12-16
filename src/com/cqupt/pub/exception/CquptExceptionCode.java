package com.cqupt.pub.exception;

public class CquptExceptionCode {
	
//custom exception 0,db.  1,web. 2,ejb. 3,sys problem.	
	 public static final int DBEXCODE = 0;

	    public static final String DBEXMSG = "DB connect problem";

	    public static final int WEBEXCODE = 1;

	    public static final String WEBEXMSG = "web problen";

	    public static final int EJBEXCODE = 2;

	    public static final String EJBEXMSG = "EJB problem ";

	    public static final int SYSEXCODE = 3;

	    public static final String SYSEXMSG = "SYS problem ";

}
