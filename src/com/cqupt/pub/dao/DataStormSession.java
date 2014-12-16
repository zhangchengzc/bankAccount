package com.cqupt.pub.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.exception.CquptExceptionCode;
import com.cqupt.pub.util.ObjectCensor;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Iterator;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import sun.jdbc.rowset.CachedRowSet;
import java.util.List;

public class DataStormSession {

	DbExcuteSql sqler; // 数据库操作类

	private ObjectCensor censor = ObjectCensor.getInstance();

	private BeanTableTranslater beanTableTranslater = BeanTableTranslater
			.getInstance();

	private static Logger logger = Logger.getLogger(DataStormSession.class);

	/**
	 * 默认数据源的DataStormSession构造函数
	 * 
	 * @throws CquptException
	 */
	private DataStormSession() throws CquptException {
		logger.debug(" into  DataStormSession()!");

		sqler = new DbExcuteSql();
		sqler.setAutoCommit(false);
	}

	/**
	 * 实例化DataStormSession
	 * 
	 * @param dataSorceJndiName
	 *            String
	 * @return DataStormSession
	 * @throws CquptException
	 */
	public static DataStormSession getInstance() throws CquptException {
		logger.debug("into getInstance(String dataSorceJndiName) !");

		DataStormSession session = null;

		session = new DataStormSession();

		return session;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @throws CquptException
	 */
	public void closeSession() throws CquptException {
		logger.debug("into closeSession()!");
		sqler.commit();
		this.sqler.closeConnection();
	}
	
	public List findSql(String sql) throws CquptException {
		logger.debug("into findSql(String sql) !");
		logger.debug("sql:" + sql);
		// 执行sql
//System.out.println("findSql"+sql);
		Object result = this.findExecute(sql);
		return beanTableTranslater.populate(result);
	}
	
	//部门树，可勾选的
	public Map<String,Object> executeSQL(String sql) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
	
		Object result = this.findExecute(sql);
			resultMap.put("resultList", beanTableTranslater.populate(result));
		
		
		return resultMap;
	}
	/*
	 * 带分页的查询
	 * pageNum页数  pageSize每页数据量
	 * 返回为一个Map,Map由两个元素组成resultList查询结果,resultCount总结果数
	 */
	public  Map<String, Object> findSql(String sql,int pageNum, int pageSize) throws CquptException {
		logger.debug("into Map<String, Object> findSql(String sql,int pageNum, int pageSize)!");
		logger.debug("sql:" + sql);
		
		// 执行sql

		int beginRow = (pageNum - 1) * pageSize + 1;
		int endROw = pageNum * pageSize;
		Object result = this.findExecute(sql);
//System.out.println(sql);
		int resultCount = ((CachedRowSet)result).size();
		sql = "SELECT  B.* FROM ( SELECT A.*, ROWNUM RN FROM ("+sql+") A WHERE ROWNUM <= "+endROw+" ) B WHERE RN >= "+beginRow;	
//System.out.println("findSql: " +sql);
		result = this.findExecute(sql);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultList", beanTableTranslater.populate(result));
		resultMap.put("resultCount",resultCount);
		return resultMap;
	}

	/**
	 * 执行sql，并将查询结果返回
	 * @param sql
	 * @return
	 * @throws CquptException
	 */
	public Object findExecute(String sql) throws CquptException {
		logger.debug("into findExecute(String sql)!");
		CachedRowSet rowset = null;
		try {

			sqler.CreatePrepareStatement(sql);
			// 执行sql操作
//System.out.println("findExecute" +sql);			
			rowset = sqler.executeQuery();
//System.out.println("rowset="+rowset);		
			
		} catch (Exception e) {
			throw new CquptException(e);
		}

		return rowset;
	}

	public void add(String sql) throws CquptException {
		logger.debug("sql:"+sql);
		this.execute(sql);
	}

	public void update(String sql) throws CquptException {
		logger.debug("sql:"+sql);
		this.execute(sql);
	}

	public void delete(String sql) throws CquptException {
		logger.debug("sql:"+sql);
		this.execute(sql);
	}

	private void execute(String sql) throws CquptException {
		logger.debug("sql:"+sql);
		sqler.CreatePrepareStatement(sql);
		sqler.executeUpdate();
	}

	/**
	 * 异常时关闭数据库连接
	 * 
	 * @throws CquptException
	 */
	public void exceptionCloseSession() throws CquptException {
		logger.debug("into exceptionCloseSession()!");
		sqler.rollback();
		this.sqler.closeConnection();


	}

	/**
	 * 关闭数据库连接
	 * 
	 * @throws CquptException
	 */
	public void closeTranSession() throws CquptException {
		logger.debug("into closeSession() !");
		this.sqler.closeConnection();
		
	}

	/**
	 * 异常时关闭数据库连接
	 * 
	 * @throws CquptException
	 */
	public void exceptionTranCloseSession() throws CquptException {
		logger.debug("into exceptionCloseSession()!");
		this.sqler.closeConnection();
		

	}

}