package com.cqupt.pub.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.exception.CquptExceptionCode;



/**
 * 创建xml文件
 * @author storm
 */
public class XMLCreater {
	private Logger logger = Logger.getLogger(this.getClass());
	
    private static XMLCreater instance = new XMLCreater();

    private XMLCreater() {
    }

    public static XMLCreater getInstance() {
        return instance;
    }

    /**
     * 创建某一节点的子节点
     * @param element
     * @param childName
     * @param childValue
     * @return
     */
    public Element createChildElement(Element element, String childName,
                                      Object childValue) {
        Element childElement = element.addElement(childName);
        if (childValue != null) {
            childElement.setText(childValue.toString());
        }
        childValue = null;
        return childElement;
    }

    /**
     * 创建xml的Document和xml文件的根
     * @return
     */
    public Document createDocument() {
        Document document = DocumentHelper.createDocument();
        document.addElement("root");

        return document;
    }
    
    /**
     * 创建elementName为名称的节点，该节点下的字节点为childList所包含的对象
     * 对象类型为Map或数据库表的映射bean
     * @param root
     * @param elementName
     * @param childList
     * @return
     * @throws DataStormException
     */
    public String createXml(String parentElementName, List childList) throws CquptException {
    	if(parentElementName == null) {
    		throw new CquptException("parentElementName为空!", CquptExceptionCode.SYSEXCODE);
    	}
    	if(childList == null) {
    		childList = new ArrayList();
    	}
    	Document document = this.createDocument();
        Element root = document.getRootElement();
        
    	for(int i=0; i<childList.size(); i++) {
    		Element element = this.createChildElement(root, parentElementName, null);
    		Object obj = childList.get(i);
    		//map时的处理
    		if(obj instanceof Map) {
    			this.createMapElement(element, obj);
    			obj = null;
    			continue;
    		} else {//bean时的处理
    			try {
    				this.createBeanElement(element, obj);
    			} catch(Exception e) {
    				throw new CquptException(e);
    			}
    		} 
    	}
    	String xml = document.asXML();
    	//logger.debug("xml=" + xml);
    	return xml;
    }
    
    /**
     * 创建xmlDocument
     * @param parentElementName
     * @param childList
     * @return
     * @throws DataStormException
     */
    public Document createDocument(String parentElementName, List childList) throws CquptException {
    	if(parentElementName == null) {
    		throw new CquptException("parentElementName为空!", CquptExceptionCode.SYSEXCODE);
    	}
    	if(childList == null) {
    		childList = new ArrayList();
    	}
    	Document document = this.createDocument();
        Element root = document.getRootElement();
        
    	for(int i=0; i<childList.size(); i++) {
    		Element element = this.createChildElement(root, parentElementName, null);
    		Object obj = childList.get(i);
    		//map时的处理
    		if(obj instanceof Map) {
    			this.createMapElement(element, obj);
    			continue;
    		} else {//bean时的处理
    			try {
    				this.createBeanElement(element, obj);
    			} catch(Exception e) {
    				throw new CquptException(e);
    			}
    		} 
    	}
    	logger.debug("xml=" + document.asXML());
    	return document;
    }
    
    /**
     * 表中的每一条数据存储在Map里的数据，生成字节点
     * @param element
     * @param obj
     */
    private void createMapElement(Element element, Object obj) {
    	Map map = (Map)obj;
    	for(Iterator it=map.entrySet().iterator();it.hasNext();) {
    		Map.Entry me = (Map.Entry) it.next();
    		Object value = me.getValue();
    		if(value instanceof java.sql.Timestamp) {
    			value = ((java.sql.Timestamp)value).toLocaleString();
    		}
//    		this.createChildElement(element, me.getKey().toString(), me.getValue());
    		this.createChildElement(element, me.getKey().toString(), value);
    	}
    }
    
    /**
     * 表中的每一条数据存储在Bean里的数据，生成字节点
     * @param element
     * @param obj
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    private void createBeanElement(Element element, Object obj) 
    	throws IllegalArgumentException, IllegalAccessException, 
    	InvocationTargetException {
    	//取得该bean的类类型
    	Class clazz = obj.getClass();
    	//取出所有的默认方法
    	Method[] methods = clazz.getDeclaredMethods();
    	//取得所有的默认属性
    	Field[] fields = clazz.getDeclaredFields();
    	//循环创建element的子节点
    	for(int i=0; i<fields.length; i++) {
    		String name = fields[i].getName();
    		String methodName = null;
    		//取得每个field对应的get方法
    		for(int j=0; j<methods.length; j++) {
    			//取得方法名
    			methodName = methods[j].getName();
    			if(methodName.indexOf("get") != -1 && 
    					methodName.toLowerCase().indexOf(name.toLowerCase()) != -1) {
    				//方法的入参
    				Object[] params = {};
    				//执行get方法
                    Object value = methods[j].invoke(obj, params);
                    if(value instanceof java.sql.Timestamp) {
                    	value = ((java.sql.Timestamp)value).toLocaleString();
                    }
                    this.createChildElement(element, name, value);
    				break;
    			}
    		}
    	}
    }
    
    /**
     * 将分页查询结果封装成xml
     * @param resultMap
     * @return
     * @throws DataStormException 
     */
    public String createXml(Map resultMap, String elementName) throws CquptException {
    	logger.debug("createXml(Map resultMap, String elementName) throws OdsException begin!");
    	logger.debug("resultMap.size()=" + resultMap.size());
    	logger.debug("elementName=" + elementName);
    	Document document = this.createDocument();
    	if(resultMap == null) {
    		return document.asXML();
    	}
    	BigDecimal allRowCount = (BigDecimal) resultMap.get("allRowCount");
    	BigDecimal total = (BigDecimal) resultMap.get("total");
    	List result = (List) resultMap.get("resultList");
    	
    	document = this.createDocument(elementName, result);
    	Element root = document.getRootElement();
    	this.createChildElement(root, "allRowCount", allRowCount);
    	this.createChildElement(root, "total", total);
    	result = null;
    	resultMap = null;
    	logger.debug("createXml(Map resultMap, String elementName) throws OdsException end!");
    	return document.asXML();
    }
}
