package com.cqupt.pub.dao;

import com.cqupt.pub.util.ObjectCensor;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.exception.CquptExceptionCode;
import sun.jdbc.rowset.CachedRowSet;
import org.apache.log4j.Logger;


import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * <p>Title: 表字段与bean属性互转</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: cqupt</p>
 *
 * @author sl
 * @version 1.0
 */
public class BeanTableTranslater {
    private ObjectCensor objCesnor = ObjectCensor.getInstance();
    private Logger logger = Logger.getLogger(this.getClass());

    private BeanTableTranslater() {
    }

    private static BeanTableTranslater instance = new BeanTableTranslater();

    protected static BeanTableTranslater getInstance() {

        return instance;
    }

    /**
     * 将bean属性转化为列名
     * @param prop
     * @return
     * @throws BssException
     */
    protected String propToCol(String prop) throws CquptException {
        if (objCesnor.checkObjectIsNull(prop)) {
            throw new CquptException("属性为空！", CquptExceptionCode.DBEXCODE);
        }

        StringBuffer sb = new StringBuffer();
        byte[] data = prop.getBytes();
        for (int i = 0; i < data.length; i++) {
            if (data[i] >= 65 && data[i] < 97) {
                sb.append("_" + (char) data[i]);
            } else {
                sb.append((char) data[i]);
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 将bean名转化为表名
     * @param beanName
     * @return
     * @throws BssException
     */
    protected String beanToTable(Class beanClazz) throws CquptException {
        String packageName = beanClazz.getPackage().getName();

        int place = packageName.length();
        String beanName = beanClazz.getName().substring(place);

        return this.propToCol(beanName).substring(2);
    }

    /**
     * 将表名转化为bean类名
     * @param tableMsg String[]
     * @return String[]
     */
    protected String tableTobean(String tableName) throws CquptException {
        if (objCesnor.checkObjectIsNull(tableName)) {
            throw new CquptException("要转换的表数据为空！", CquptExceptionCode.DBEXCODE);
        }
        String tableNameTemp = tableName.toUpperCase();
        String[] names = tableNameTemp.split("_");
        StringBuffer sb = new StringBuffer(names[0]);
        for (int i = 1; i < names.length; i++) {
            sb.append(names[i].substring(0, 1).toUpperCase() +
                      names[i].substring(1).toLowerCase());
        }

        return sb.toString();
    }

    /**
     * 将表的列名转化为类的属性名
     * @param tableMsg String[]
     * @return String[]
     */
    protected String colToProp(String tableColumn) throws CquptException {
        if (objCesnor.checkObjectIsNull(tableColumn)) {
            throw new CquptException("要转换的列数据为空！", CquptExceptionCode.DBEXCODE);
        }
        String tableColumnTemp = tableColumn.toLowerCase();
        String[] names = tableColumnTemp.split("_");
        StringBuffer sb = new StringBuffer(names[0]);
        for (int i = 1; i < names.length; i++) {
            sb.append(names[i].substring(0, 1).toUpperCase() +
                      names[i].substring(1).toLowerCase());
        }

        return sb.toString();
    }

    /**
     * 将数据库中取得的结果集装入map中
     * @param result Object
   
     */
    protected List populate(Object result) throws CquptException {
        logger.debug("into populate(Object result)!");
        logger.debug("result:" + result);    

        if(objCesnor.checkObjectIsNull(result)) {
            throw new CquptException("result为空！",CquptExceptionCode.DBEXCODE);
        }

        List beanList = new ArrayList();
        try {
            CachedRowSet rowset = (CachedRowSet)result;

            while(rowset.next()) {
               HashMap resultMap = new HashMap();
               int colCount = rowset.getMetaData().getColumnCount();

               for(int i=0; i<colCount; i++) {
                   String colName = rowset.getMetaData().getColumnName(i+1);
                   Object value = rowset.getObject(colName);
                   this.nullToObject(value);
                   String prop = this.colToProp(colName);
                   resultMap.put(prop, value);
               }
               beanList.add(resultMap);
            }
        } catch(Exception e) {
            throw new CquptException(e);
        }

        return beanList;
    }
    /**
     * 将数据库中取得的结果集装入给定的bean中
     * @param clazz Class
     * @param rowset Object
     * @return Object
     */
    protected List populate(Class clazz, Object result) throws CquptException {
        logger.debug("populate(Class clazz, Object result) begin!");
        logger.debug("clazz:" + clazz);
        logger.debug("result:" + result);

        if(objCesnor.checkObjectIsNull(clazz)) {
            throw new CquptException("clazz为空！", CquptExceptionCode.DBEXCODE);
        }

        if(objCesnor.checkObjectIsNull(result)) {
            throw new CquptException("result为空！",CquptExceptionCode.DBEXCODE);
        }

        List beanList = new ArrayList();
        try {
            CachedRowSet rowset = (CachedRowSet)result;

            while(rowset.next()) {
               Object bean = clazz.newInstance();
               int colCount = rowset.getMetaData().getColumnCount();

               for(int i=0; i<colCount; i++) {
            	   String colName = rowset.getMetaData().getColumnName(i+1);
            	   //Object value = rowset.getObject(i);
                   //根据colName对应的属性的数据类型在rowset取相应类型的数据
            	   Object value = this.getRowSetValue(clazz, colName, rowset);
            	   this.nullToObject(value);
                   this.setProperty(colName, value, bean);
               }
               beanList.add(bean);
            }
        } catch(Exception e) {
            throw new CquptException(e);
        }
        logger.debug("populate(Class clazz, Object result) end!");
        return beanList;
    }
    
    /**
     * 根据colName对应的属性的数据类型在rowset取相应类型的数据
     * @param clazz
     * @param colName
     * @param rowset
     * @return
     * @throws CquptException 
     */
    private Object getRowSetValue(Class clazz, String colName, CachedRowSet rowset) throws CquptException {
    	logger.debug("into getRowSetValue(Class clazz, String colName, CachedRowSet rowset) !");
        logger.debug("colName:" + colName);
        logger.debug("clazz=" + clazz);

        if(objCesnor.checkObjectIsNull(colName)) {
            throw new CquptException("colName为空！", CquptExceptionCode.DBEXCODE);
        }
        
        if(objCesnor.checkObjectIsNull(rowset)) {
            throw new CquptException("rowset为空！", CquptExceptionCode.DBEXCODE);
        }
        try {
        	String property = this.colToProp(colName);
        	String propertyType = clazz.getDeclaredField(property).getType().getName();
        	if(propertyType.equals("java.math.BigDecimal")) {
        		return rowset.getBigDecimal(colName);
        	} else if(propertyType.equals("java.lang.String")) {
        		return rowset.getString(colName);
        	} else if(propertyType.equals("java.sql.Timestamp")) {
        		return rowset.getObject(colName);
//        	} else if(propertyType.equals("java.sql.Boolean")) {
//        		return new Boolean(rowset.getBoolean(colName));
        	} else if(propertyType.equals("class [B") || 
        			propertyType.equals("[B")) {
        		return rowset.getBytes(colName);
        	} else {
        		throw new CquptException(propertyType + "数据类型系统未定义！" +
        				"数据类型定义请参考SysParamter类中以SQL_WHERE开头的常量", CquptExceptionCode.DBEXCODE);
        	}
        } catch(Exception e) {
        	throw new CquptException(e);
        }
    }

    /**
     * 将取得的某一列数据set到相应的bean实例属性中
     * @param colName String
     * @param value String
     * @param bean String
     * @throws CquptException
     */
    private void setProperty(String colName, Object value, Object bean) throws CquptException {
        logger.debug(" into  setProperty(String colName, String value) !");
        logger.debug("colName:" + colName);
        logger.debug("value:" + value);
				
        if(objCesnor.checkObjectIsNull(colName)) {
            throw new CquptException("colName为空！", CquptExceptionCode.DBEXCODE);
        }
        if(objCesnor.checkObjectIsNull(value)) {
        	return;
        }
        
        String property = this.colToProp(colName);
        Class[] paramTypes = { value.getClass() };
        Object[] objs = { value };
        Class clazz = bean.getClass();

        String methodName = "set" + property.substring(0, 1).toUpperCase() +
                            property.substring(1);
        try {
            Method method = clazz.getMethod(methodName, paramTypes);
            method.invoke(bean, objs);
        } catch (Exception ex) {
            throw new CquptException(ex);
        }

    }
    
    private Object nullToObject(Object value) {
    	if(this.objCesnor.checkObjectIsNull(value)) {
	    	if(value instanceof String) {
	    		value = "";
	    	} else if(value instanceof java.math.BigDecimal) {
	    		value = new BigDecimal("0");
	    	} else if(value instanceof java.sql.Timestamp) {
	    		value = new Timestamp(0);
	    	}
    	}
    	
    	return value;
    }
}
