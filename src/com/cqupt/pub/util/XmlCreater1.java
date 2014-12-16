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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.exception.CquptExceptionCode;



public class XmlCreater1 {
	private Logger logger = Logger.getLogger(this.getClass());
	private XMLCreater xmlCreater = XMLCreater.getInstance();
	private static XmlCreater1 instance = new XmlCreater1();
	
	private XmlCreater1() {}	
	
	public static XmlCreater1 getInstance() {
		return instance;
	}
	
	public String createMapXml(Map resultMap) throws CquptException {
        logger.debug("createMapXml(Map resultMap) begin!");
        String xml = this.createXml(resultMap, "resultList");
        resultMap = null;

        logger.debug("createMapXml(Map resultMap) end!");

        return xml;
    }
	
	public String createMapXml(Map resultMap,String extraTag[]) throws CquptException {
        logger.debug("createMapXml(Map resultMap) begin!");
        String xml = this.createXml(resultMap, "resultList",extraTag);
        resultMap = null;

        logger.debug("createMapXml(Map resultMap) end!");

        return xml;
    }
	
	public String createListXml(List resultList) throws CquptException {
        logger.debug("createListXml(List resultList) begin!");
        logger.debug("resultList=" + resultList);
        String xml = this.xmlCreater.createXml("result", resultList);
        resultList = null;
        logger.debug("xml = " + xml);
        logger.debug("createListXml(List resultList) end!");

        return xml;
    }
	
    public String createExXml(java.lang.Exception ex) {
        logger.debug("createExXml(Exception ex) begin!");
        String exMsg = (new CquptException(ex)).getExceptionMsg();
        Document document = xmlCreater.createDocument();
        Element root = document.getRootElement();
        xmlCreater.createChildElement(root, "exMsg", exMsg);

        logger.debug("createExXml(Exception ex) end!");
        return document.asXML();
    }
    
    public String createNullXml(String exMsg) {
    	
    	
    	
        logger.debug("createNullXml begin!");
        Document document = xmlCreater.createDocument();
        Element root = document.getRootElement();
        xmlCreater.createChildElement(root, "allRowCount", "0");
        xmlCreater.createChildElement(root, "exMsg", exMsg);

        logger.debug("createNullXml end!");
        return document.asXML();
    }
    
public String createNull1Xml(Map resultMap, String elementName, String exMsg) {
    	
	 	Document document = this.createDocument();
		if(resultMap == null) {
			return document.asXML();
		}
		BigDecimal allRowCount = (BigDecimal) resultMap.get("allRowCount");
		List result = (List) resultMap.get("resultList");
		try {
			document = this.createDocument(elementName, result);
		} catch (CquptException e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		this.createChildElement(root, "allRowCount", allRowCount);
		xmlCreater.createChildElement(root, "exMsg", exMsg);
		logger.debug("createXml end");
		result = null;
		resultMap = null;
		logger.debug("createXml(Map resultMap, String elementName) throws DataStormException end!");
		String xml = document.asXML();
    	
        logger.debug("createNullXml begin!");
      
        return document.asXML();
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
    	logger.debug("childList.size()=" + childList.size());
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
    				obj = null;
    			} catch(Exception e) {
    				throw new CquptException(e);
    			}
    		}
    	}
    	
    	//logger.debug("xml=" + document.asXML());
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
    	logger.debug("clazz=" + clazz);
    	//取出所有的默认方法
    	Method[] methods = clazz.getDeclaredMethods();
    	//取得所有的默认属性
    	Field[] fields = clazz.getDeclaredFields();
    	//循环创建element的子节点
    	for(int i=0; i<fields.length; i++) {
    		String name = fields[i].getName();
    		logger.debug("name=" + name);
    		String methodName = null;
    		//取得每个field对应的get方法
    		for(int j=0; j<methods.length; j++) {
    			//取得方法名
    			methodName = methods[j].getName();
    			logger.debug("methodName=" + methodName);
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
     * 创建某一节点的子节点
     * @param element
     * @param childName
     * @param childValue
     * @return
     */
    public Element createChildElement(Element element, String childName,
                                      Object childValue) {
    	//logger.debug("createChildElement(Element element, String childName, Object childValue) begin!");
    	//logger.debug("childName=" + childName);
    	
        Element childElement = element.addElement(childName);
        //logger.debug("addElement end");
        if (childValue != null) {
            childElement.setText(childValue.toString());
        }
        
        childValue = null;
        
        //logger.debug("createChildElement(Element element, String childName, Object childValue) end!");
        return childElement;
    }
    
    /**
     * 将分页查询结果封装成xml
     * @param resultMap
     * @return
     * @throws DataStormException 
     */
    public String createXml(Map resultMap, String elementName) throws CquptException {
    	logger.debug("createXml(Map resultMap, String elementName) throws DataStormException begin!");
    	logger.debug("resultMap.size()=" + resultMap.size());
    	logger.debug("elementName=" + elementName);
    	Document document = this.createDocument();
    	if(resultMap == null) {
    		return document.asXML();
    	}
    	BigDecimal allRowCount = (BigDecimal) resultMap.get("allRowCount");
    	List result = (List) resultMap.get("resultList");
    	document = this.createDocument(elementName, result);
    	Element root = document.getRootElement();
    	this.createChildElement(root, "allRowCount", allRowCount);
    	logger.debug("createXml end");
    	result = null;
    	resultMap = null;
    	logger.debug("createXml(Map resultMap, String elementName) throws DataStormException end!");
    	String xml = document.asXML();
    	logger.debug("create xml end");
    	return xml;
    }
    /**
     * 将分页查询结果封装成xml,同时将额外的节点放到Xml里面去
     * @param resultMap
     * @return
     * @throws DataStormException 
     */
    public String createXml(Map resultMap, String elementName,String extraTag[]) throws CquptException {
    	logger.debug("createXml(Map resultMap, String elementName) throws DataStormException begin!");
    	logger.debug("resultMap.size()=" + resultMap.size());
    	logger.debug("elementName=" + elementName);
    	Document document = this.createDocument();
    	if(resultMap == null) {
    		return document.asXML();
    	}
    	BigDecimal allRowCount = (BigDecimal) resultMap.get("allRowCount");
    	List result = (List) resultMap.get("resultList");
    	document = this.createDocument(elementName, result);
    	Element root = document.getRootElement();
    	this.createChildElement(root, "allRowCount", allRowCount);
    	//处理额外的标签
    	int ij = 0;
    	for(ij = 0;ij < extraTag.length; ij++){
	    	String extraValue = (String)resultMap.get(extraTag[ij]);
	    	if(null != extraValue)
	    		createChildElement(root, extraTag[ij], extraValue);
    	}
    	logger.debug("createXml end");
    	result = null;
    	resultMap = null;
    	logger.debug("createXml(Map resultMap, String elementName) throws DataStormException end!");
    	String xml = document.asXML();
    	logger.debug("create xml end");
    	return xml;
    }
}
