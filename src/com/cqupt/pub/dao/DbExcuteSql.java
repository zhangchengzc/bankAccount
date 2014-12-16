package com.cqupt.pub.dao;

import java.sql.*;
import java.io.InputStream;
import java.math.*;
import sun.jdbc.rowset.CachedRowSet;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.exception.CquptExceptionCode;
import com.cqupt.pub.util.ObjectCensor;

/**
 * 数据库操作类，将sql传给该类，即可操作数据库，包括增、删、查、改及存储过程的执行
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: raining</p>
 *
 * @author df
 * @version 1.0
 */
public class DbExcuteSql {
    private DbConnection dbConn = new DbConnection();

    //数据库连接
    private Connection conn = null;

    private Statement stmt = null;

    private PreparedStatement prepstmt = null;
    
    private ResultSet rs = null;

    private ObjectCensor censor = ObjectCensor.getInstance();

    private java.sql.CallableStatement callableStmt;

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 创建默认数据库连接的Statement
     * @throws Exception
     * @throws SQLException
     * @throws DBConnectionException
     */
    protected DbExcuteSql() throws CquptException {
        logger.debug(" into DBExcuteSQLer() 初始化数据库连接 ");
        try {
            this.conn = dbConn.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            throw new CquptException(e);
        }
    }


    /**
     * 根据jndiName得到的connection创建Statement
     * @param jndiName String
     * @param sql String
     * @throws SQLException
     * @throws Exception
     */

    /**
     * 创建默认数据库连接的PreparedStatement
     * @param sql String
     * @throws SQLException
     */
    public void CreatePrepareStatement(String sql) throws CquptException {
        logger.debug("into CreatePrepareStatement(String sql) 创建pstmt ");

        if (censor.checkObjectIsNull(sql)) {
            throw new CquptException("sql 语句为空！", CquptExceptionCode.DBEXCODE);
        }

        if (censor.checkObjectIsNull(conn)) {
            throw new CquptException("数据库连接为空！", CquptExceptionCode.DBEXCODE);
        }
        logger.debug("sql: " + sql);
        try {
//System.out.println("prepstmt"+sql);
	        prepstmt = this.conn.prepareStatement(sql,
	                                              ResultSet.TYPE_SCROLL_INSENSITIVE,
	                                              ResultSet.CONCUR_READ_ONLY);
//System.out.println("prepstmt"+prepstmt);
        } catch(Exception e) {
        	throw new CquptException(e);
        }
       
    }

    /**
     * 创建存储过程
     * @param sql
     * @throws SQLException
     */
    public void createCallableStatement(String sql) throws CquptException {
        logger.debug("into createCallableStatement(String sql) 创建CallableStatement");

        if (censor.checkObjectIsNull(sql)) {
            throw new CquptException("sql 语句为空！", CquptExceptionCode.DBEXCODE);
        }
        logger.debug("sql: " + sql);
        try {
        	callableStmt = conn.prepareCall(sql);
        } catch(Exception e) {
        	throw new CquptException(e);
        }
    }

    /**
     * 存储过程出参注册
     * @param parameterIndex
     * @param sqlType
     * @throws SQLException
     */
    public void registerOutParameter(int parameterIndex, int sqlType) throws
            SQLException {
        logger.debug("into registerOutParameter(int parameterIndex, int sqlType) ");
        logger.debug("parameterIndex:" + parameterIndex);
        logger.debug("sqlType: " + sqlType);

        callableStmt.registerOutParameter(parameterIndex, sqlType);
        
    }

    /**
     * 存储过程入参
     * @param index
     * @param value
     * @throws SQLException
     */
    public void setCallableString(int index, String value) throws SQLException {
        logger.debug("into setCallableString(int index, String value) ");
        logger.debug("index:" + index);
        logger.debug("value: " + value);
        callableStmt.setString(index, value);
       
    }

    /**
     * 存储过程入参
     * @param index
     * @param value
     * @throws SQLException
     */
    public void setCallableBigDecimal(int index, BigDecimal value) throws
            SQLException {
        logger.debug("into setCallableBigDecimal(int index, BigDecimal value) ");
        logger.debug("index:" + index);
        logger.debug("value: " + value);
        if (this.censor.checkObjectIsNull(value)) {
            throw new SQLException("BigDecimal型value为空！");
        }
        callableStmt.setBigDecimal(index, value);

       
    }

    /**
     * 存储过程入参
     * @param index
     * @param value
     * @throws SQLException
     */
    public void setCallableTimestamp(int index, Timestamp value) throws
            SQLException {
        logger.debug("into setCallableTimestamp(int index, Timestamp value) ");
        logger.debug("index:" + index);
        logger.debug("value: " + value);
        if (this.censor.checkObjectIsNull(value)) {
            throw new SQLException("Timestamp型value为空！");
        }
        callableStmt.setTimestamp(index, value);

        
    }

    /**
     * 存储过程入参
     * @param index
     * @param iType
     * @throws SQLException
     */
    public void setCallableNULL(int index, int iType) throws SQLException {
        logger.debug("into setCallableNULL(int index, int iType) ");
        logger.debug("index:" + index);
        logger.debug("iType: " + iType);

        callableStmt.setNull(index, iType);

    }

    /**
     * 执行存储过程
     * @return
     * @throws SQLException
     */
    public boolean callableExecute() throws SQLException {
        logger.debug("into callableExecute()");
      
        return callableStmt.execute();
    }

    /**
     * 取得存储过程出参
     * @param index
     * @return
     * @throws SQLException
     */
    public String getCallableString(int index) throws SQLException {
        logger.debug("into getCallableString(int index) ");
        logger.debug("index: " + index);

        if (callableStmt != null) {         
            return callableStmt.getString(index);
        } else {          
            return "-1";
        }
    }

    /**
     * 取得存储过程出参
     * @param index
     * @return
     * @throws SQLException
     */
    public BigDecimal getCallableBigDecimal(int index) throws SQLException {
        logger.debug("into getCallableBigDecimal(int index) ");
        logger.debug("index: " + index);
        return callableStmt.getBigDecimal(index);
    }

    /**
     * 取得存储过程出参
     * @param index
     * @return
     * @throws SQLException
     */
    public Timestamp getCallableTimestamp(int index) throws SQLException {
        logger.debug("into getCallableTimestamp(int index) ");
        logger.debug("index: " + index);
      
        return callableStmt.getTimestamp(index);
    }

    /**
     * 设置对应值
     * @param index 参数索引
     * @param value 对应值
     */
    public void setString(int index, String value) throws SQLException {
        logger.debug("into setString(int index, String value) ");
        logger.debug("index: " + index);
        logger.debug("value: " + value);
        this.checkPrepstmtIsNull(prepstmt);
        prepstmt.setString(index, value);

    }

    /**
     * 设置对应值
     * @param index 参数索引
     * @param value 对应值
     */
    public void setInt(int index, int value) throws SQLException {
        logger.debug("into setInt(int index, int value) ");
        logger.debug("index: " + index);
        logger.debug("value: " + value);

        this.checkPrepstmtIsNull(prepstmt);
        prepstmt.setInt(index, value);

    }

    /**
     * 设置对应值
     * @param index 参数索引
     * @param value 对应值
     */
    public void setBoolean(int index, boolean value) throws SQLException {
        logger.debug("into setBoolean(int index, boolean value)");
        logger.debug("index: " + index);
        logger.debug("value: " + value);

        this.checkPrepstmtIsNull(prepstmt);
        prepstmt.setBoolean(index, value);

    }

    /**
     * 设置对应值
     * @param index 参数索引
     * @param value 对应值
     */
    public void setDate(int index, java.sql.Date value) throws SQLException {
        logger.debug("into setDate(int index, java.sql.Date value)");
        logger.debug("index: " + index);
        logger.debug("value: " + value);

        this.checkPrepstmtIsNull(prepstmt);
        prepstmt.setDate(index, value);

    }

    /**
     * 设置对应值
     * @param index 参数索引
     * @param value 对应值
     */
    public void setLong(int index, long value) throws SQLException {
        logger.debug("into setLong(int index, long value) ");
        logger.debug("index: " + index);
        logger.debug("value: " + value);

        this.checkPrepstmtIsNull(prepstmt);
        prepstmt.setLong(index, value);

    }

    /**
     * 设置对应值
     * @param index 参数索引
     * @param value 对应值
     */
    public void setFloat(int index, float value) throws SQLException {
        logger.debug("into setFloat(int index, float value)");
        logger.debug("index: " + index);
        logger.debug("value: " + value);

        this.checkPrepstmtIsNull(prepstmt);
        prepstmt.setFloat(index, value);

    }

    /**
     * 设置对应值
     * @param index 参数索引
     * @param value 对应值
     */
    public void setBigDecimal(int index, BigDecimal value) throws SQLException {
        logger.debug("into setBigDecimal(int index, setBigDecimal value) ");
        logger.debug("index: " + index);
        logger.debug("value: " + value);

        this.checkPrepstmtIsNull(prepstmt);
        prepstmt.setBigDecimal(index, value);

    }

    /**
     * 设置对应值
     * @param index 参数索引
     * @param value 对应值
     */
    public void setTimestamp(int index, Timestamp value) throws SQLException {
        logger.debug("into setTimestamp(int index, Timestamp value)");
        logger.debug("index: " + index);
        logger.debug("value: " + value);

        this.checkPrepstmtIsNull(prepstmt);
        prepstmt.setTimestamp(index, value);

    }

    /**
     * 设置对应值
     * @param index 参数索引
     * @param value 对应值
     */
    public void setNULL(int index, int iType) throws SQLException {
        logger.debug("into setNULL(int index, int iType)");
        logger.debug("index: " + index);
        logger.debug("iType: " + iType);

        this.checkPrepstmtIsNull(prepstmt);
        prepstmt.setNull(index, iType);

    }

    /**
     * 设置对应值
     * @param index 参数索引
     * @param value 对应值
     */
    public void setBytes(int index, byte[] bytes) throws SQLException {
        logger.debug("into setBytes(int index, byte[] bytes)");
        if (this.censor.checkObjectIsNull(bytes)) {
            throw new SQLException("bytes数组为空！");
        }
        logger.debug("index: " + index);
        logger.debug("byte[]: " + bytes);

        this.checkPrepstmtIsNull(prepstmt);
        prepstmt.setBytes(index, bytes);

    }

    /**
     * 设置对应值
     * @param index 参数索引
     * @param value 对应值
     */
    public void setBinaryStream(int index, InputStream in, int arg) throws  SQLException {
        logger.debug( "into setBinaryStream(int index, InputStream in, int arg)");
        if (this.censor.checkObjectIsNull(in)) {
            throw new SQLException("in输入流为空！");
        }
        logger.debug("index: " + index);
        logger.debug("arg: " + arg);

        this.checkPrepstmtIsNull(prepstmt);
        prepstmt.setBinaryStream(index, in, arg);

    }

    /**
     * 执行SQL语句返回字段集
     * @param sql SQL语句
     * @return ResultSet 字段集
     */
    public CachedRowSet executeQuery(String sql) throws SQLException {
        logger.debug("into executeQuery(String sql)");
        if (censor.checkObjectIsNull(sql)) {
            throw new SQLException("sql为空！");
        }
        logger.debug("sql: " + sql);

        try {
            if (stmt != null) {
                CachedRowSet cachedRowSet = new CachedRowSet();
                rs = stmt.executeQuery(sql);
                cachedRowSet.populate(rs);
              
                return cachedRowSet;
            } else {
                throw new SQLException("数据库连接为空！");
            }

        } catch (SQLException e) {
        	if(rs != null) {
        		rs.close();
        	}
            if (stmt != null) {
                stmt.close();
            }
            throw e;
        }
    }

    /**
     *执行查询，返回ResultSet结果
     * @return
     * @throws SQLException
     */
    public CachedRowSet executeQuery() throws CquptException {
        logger.debug("into executeQuery() ");
        try {
            if (prepstmt != null) {
                CachedRowSet cachedRowSet = new CachedRowSet();
                rs = prepstmt.executeQuery();
                cachedRowSet.populate(rs);
             
                return cachedRowSet;
            } else {
                throw new SQLException("数据库连接为空！");
            }
        } catch (Exception e) {
        	try {
	        	if(rs != null) {
	        		rs.close();
	        	}
	            if (stmt != null) {
	            	stmt.close();
	            }
        	} catch(Exception ex) {}
            throw new CquptException(e);
        }
    }

    /**
     * 执行SQL语句
     * @param sql SQL语句
     */
    public int executeUpdate(String sql) throws SQLException {
        logger.debug("into executeUpdate(String sql) ");
        if (censor.checkObjectIsNull(sql)) {
            throw new SQLException("sql为空！");
        }
        logger.debug("sql: " + sql);

        try {
            if (stmt != null) {             
                return stmt.executeUpdate(sql);
            } else {
                throw new SQLException("数据库连接为空！");
            }
        } catch (SQLException e) {
            if (stmt != null) {
                stmt.close();
            }
            throw e;
        }
    }

    /**
     * 增加批量更新的sql语句
     * @param sql String
     * @throws SQLException
     */
    public void addBatch(String sql) throws SQLException {
        logger.debug("into addBatch(String sql)");
        if (censor.checkObjectIsNull(sql)) {
            throw new SQLException("sql为空！");
        }
        logger.debug("sql: " + sql);
        try {
            if (stmt != null) {
                stmt.addBatch(sql);

            } else {
                throw new SQLException("数据库连接为空！");
            }
        } catch (SQLException e) {
            if (stmt != null) {
                stmt.close();
            }
            throw e;
        }
    }

    /**
     * 执行批量更新sql语句
     * @throws SQLException
     * @return int[]
     */
    public int[] executeBatch() throws SQLException {
        logger.debug("into executeBatch(String sql) ");
        try {
            if (stmt != null) {
                return stmt.executeBatch();
            } else {
                throw new SQLException("数据库连接为空！");
            }
        } catch (SQLException e) {
            if (stmt != null) {
                stmt.close();
            }

            throw e;
        }
    }

    /**
     * 清空批量更新sql语句
     * @throws SQLException
     * @return int[]
     */
    public void clearBatch() throws SQLException {
        logger.debug("into clearBatch()");
        try {
            if (stmt != null) {
                stmt.clearBatch();
            } else {
                throw new SQLException("数据库连接为空！");
            }
           
        } catch (SQLException e) {
            if (stmt != null) {
                stmt.close();
            }

            throw e;
        }
    }

    /**
     * 执行sql语句
     * @return 执行结果行数
     * @throws SQLException
     */
    public void executeUpdate() throws CquptException {
        logger.debug("into executeUpdate()");
        try {
            if (prepstmt != null) {
                prepstmt.executeUpdate();
            } else {
                throw new CquptException("数据库连接为空！", CquptExceptionCode.DBEXCODE);
            }
        } catch (Exception e) {
            if (stmt != null) {
            	try {
                stmt.close();
            	} catch(Exception ex) {
            		throw new CquptException(ex);
            	} 
            }
            throw new CquptException(e);
        }
    }

    /**
     * 关闭连接
     * @throws SQLException
     */
    public void closeStmt() throws CquptException {
        logger.debug("into closeStmt() ");
        try {
	        if (stmt != null) {
	            stmt.close();
	        }
	        if (prepstmt != null) {
	            prepstmt.close();
	        }
        } catch(Exception e) {
        	throw new CquptException(e);
        }
 
    }

    /**
     * 设置是否自动提交
     * @param auto
     * @throws SQLException
     */
    public void setAutoCommit(boolean auto) throws CquptException {
        logger.debug("into setAutoCommit() ");
        try {
	        if (conn != null) {
	            conn.setAutoCommit(auto);
	        }
        } catch(SQLException e) {
        	throw new CquptException(e);
        }
    }

    /**
     * 提交数据库操作
     * @throws SQLException
     */
    public void commit() throws CquptException {
        logger.debug("into commit()");
        try {
	        if (conn != null) {
	            conn.commit();
	        }
        } catch(SQLException e) {
        	throw new CquptException(e);
        }
    }

    /**
     * 回滚数据库操作
     * @throws SQLException
     */
    public void rollback() throws CquptException {
        logger.debug("into rollback()");
        try {
	        if (conn != null) {
	            conn.rollback();
	        }
	    } catch(SQLException e) {
	    	throw new CquptException(e);
	    }
    }

    /**
     * 关闭连接
     * @throws CquptException
     */
    public void closeConnection() throws CquptException {
		logger.debug("into closeConnection()");
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (prepstmt != null) {
				prepstmt.close();
				prepstmt = null;

			}
			dbConn.releaseConnection(conn);
		} catch (Exception ex) {
			throw new CquptException(ex);
		}
	
	}

    /**
	 * 检查PreparedStatement是否为空
	 * 
	 * @param preStmt
	 *            PreparedStatement
	 * @throws SQLException
	 */
    private void checkPrepstmtIsNull(PreparedStatement preStmt) throws
            SQLException {
        logger.debug("into checkPrepstmtIsNull(PreparedStatement preStmt)");
        if (censor.checkObjectIsNull(preStmt)) {
            throw new SQLException("preStmt为空！");
        }
        if (censor.checkObjectIsNull(preStmt)) {
            throw new SQLException("数据库连接为空！");
        }

    }
}
