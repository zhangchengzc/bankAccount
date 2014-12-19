package com.cqupt.pub.dao;

import java.sql.*; 

import org.apache.log4j.Logger;

import com.cqupt.pub.exception.CquptException;

public final class DbConnection { 
   
      private  String driver = "com.mysql.jdbc.Driver"; 
      // private  String url = "jdbc:mysql://111.10.24.77:3306/cqmass?characterEncoding=gb2312";//?characterEncoding=utf8
        // private  String url = "jdbc:mysql://172.23.8.200:3306/cqmass?characterEncoding=gb2312";//?characterEncoding=utf8
       // private  String url = "jdbc:mysql://127.0.0.1:3306/cqmass?characterEncoding=gb2312";
        //   private  String url = "jdbc:mysql://172.23.8.75:3306/cqmass?characterEncoding=gb2312";
      private  String url = "jdbc:mysql://localhost:3306/bankaccount?characterEncoding=UTF-8";//?characterEncoding=utf8
      private  String user = "root"; 
      private  String password = "131518zc"; 
      private Logger logger =  Logger.getLogger(this.getClass());
	      
      public DbConnection() {
      }
      // connect datebase mysql 
      public Connection getConnection() throws CquptException {
    	  logger.debug("into getConnection() ");
    	  Connection conn = null;
    	  
    	  try {
    		 
    		  Class.forName(driver);
    		  conn = DriverManager.getConnection(url,user,password);

    		  return conn ;
    	  }catch (Exception ex){
    		  throw new CquptException(ex);
    	  }   	  
      }
      
      // releace datebase mysql
      public boolean releaseConnection(Connection conn){
    	  logger.debug("into releaseConnection(Connection conn)  ");
    	  try{
    		  if(conn != null){
    			  conn.close();
    			  conn = null;
    		  }
    	 
    	  }catch(Exception e){
    		  logger.error(e.getMessage());
    		  return false;
    	  }
    	  return true;
      }
      
          
         
      
}
