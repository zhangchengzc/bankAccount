package com.cqupt.sysManger.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.dom4j.Document;
import org.dom4j.Element;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.exception.CquptExceptionCode;
import com.cqupt.pub.util.ObjectCensor;
import com.cqupt.pub.util.ObjectChanger;
import com.cqupt.pub.util.XMLCreater;



public class PermissiondomDao {

	private XMLCreater xmlCreater = XMLCreater.getInstance();
	private ObjectCensor objCensor = ObjectCensor.getInstance();
	private ObjectChanger objChanger = ObjectChanger.getInstance();
	
	/**
	 * 创建权限树
	 * @return
	 * @throws DataStormException
	 */
	public String createPopedomTree() throws CquptException {
		
		String xml = "";
		DataStormSession session = null;
		try {
			session = DataStormSession.getInstance();
			String sql = "select t.menuid ELEMENT_ID,t.menuname ELEMENT_NAME,t.url ELEMENT_URL,t.menuleave ELEMENT_GRADE ,t.p_menuid P_ELEMENT_ID,t.meun_title ELEMENT_TITLE,t.meun_desc ELEMENT_DESC,t.menu_order ELEMENT_ORDER  from sys_menu t ORDER BY t.menuleave,t.menu_order ";

			List resultList = session.findSql(sql);
	
			Map resultMap;
			List popedomList = new ArrayList();
			
			for(int i = 0; i<resultList.size(); i++) {
				resultMap = (Map)resultList.get(i);
	
				Popedom popedom = new Popedom();
				popedom.setElementDesc(resultMap.get("elementDesc").toString());
				popedom.setElementGrade(resultMap.get("elementGrade").toString());
				popedom.setElementId(resultMap.get("elementId").toString());
				popedom.setElementName(resultMap.get("elementName").toString());
				popedom.setElementOrder(resultMap.get("elementOrder").toString());
				popedom.setElementTitle(resultMap.get("elementTitle").toString());
				popedom.setElementUrl(resultMap.get("elementUrl").toString());
				popedom.setPElementId(resultMap.get("pElementId").toString());
				

				popedomList.add(popedom);
				
			}
			
	
			xml = this.createPopedomXml(popedomList);
			session.closeSession();
		} catch (Exception e) {
			if (session != null) {
				session.exceptionCloseSession();
			}
			throw new CquptException(e);
		}
		
		return xml;
	}
	
	
	/**
	 * 创建权限树xml
	 * @return
	 */
	public String createPopedomXml(List popedomList) {
	
		String xml = "";
		Document document = xmlCreater.createDocument();
		Element root = document.getRootElement();
		
		for(int i=0; i<popedomList.size(); i++) {
			Popedom firstPopedom = (Popedom) popedomList.get(i);
			String firstElementId = firstPopedom.getElementId();
			
			String firstElementGrade = firstPopedom.getElementGrade();
			
			if("1".equals(firstElementGrade.trim())) {
				Element first = this.xmlCreater.createChildElement(root, "first", null);
				
				this.xmlCreater.createChildElement(first, "name", firstPopedom.getElementName());
				this.xmlCreater.createChildElement(first, "id", firstPopedom.getElementId());
				for(int ii=0; ii<popedomList.size(); ii++) {
					Popedom twoPopedom = (Popedom) popedomList.get(ii);
					String twoElementGrade = twoPopedom.getElementGrade();
				
					if("2".equals(twoElementGrade.trim()) && firstElementId.equals(twoPopedom.getPElementId())) {
						String twoElementId = twoPopedom.getElementId();
				
						Element two = this.xmlCreater.createChildElement(first, "second", null);
					
						this.xmlCreater.createChildElement(two, "name", twoPopedom.getElementName());
						this.xmlCreater.createChildElement(two, "id", twoPopedom.getElementId());
						for(int iii=0; iii<popedomList.size(); iii++) {
							Popedom threePopedom = (Popedom) popedomList.get(iii);
							String threeElementGrade = threePopedom.getElementGrade();
						
							if("3".equals(threeElementGrade.trim()) && twoElementId.equals(threePopedom.getPElementId())) {
							
								Element three = this.xmlCreater.createChildElement(two, "third", null);
								
								this.xmlCreater.createChildElement(three, "name", threePopedom.getElementName());
								this.xmlCreater.createChildElement(three, "id", threePopedom.getElementId());
							}
						}
					}
				}
			}
			
		}
		xml = document.asXML();
		
		return xml;
	}
	
	/**
	 * 根据某一节点Id，取得该节点信息
	 * @param elementId
	 * @return
	 * @throws DataStormException 
	 */
	public List showPopedom(String elementId) throws CquptException {
	
		if(this.objCensor.checkObjectIsNull(elementId)) {
			elementId = "-1";
		}
		DataStormSession session = null;   
		List popedomList = new ArrayList();
		
		try {
			session = DataStormSession.getInstance();
			String sql = "select t.menuid ELEMENT_ID,t.menuname ELEMENT_NAME,t.url ELEMENT_URL,t.menuleave ELEMENT_GRADE ,t.p_menuid P_ELEMENT_ID,t.meun_title ELEMENT_TITLE,t.meun_desc ELEMENT_DESC,t.menu_order ELEMENT_ORDER  from sys_menu t where menuid='"+elementId+"'";
System.out.println("sql:"+sql);
			List resultList = session.findSql(sql);			
			Map resultMap;		
			for(int i = 0; i<resultList.size(); i++) {
				resultMap = (Map)resultList.get(i);
				Popedom popedom = new Popedom();
				popedom.setElementDesc(resultMap.get("elementDesc").toString());
				popedom.setElementGrade(resultMap.get("elementGrade").toString());
				popedom.setElementId(resultMap.get("elementId").toString());
				popedom.setElementName(resultMap.get("elementName").toString());
				popedom.setElementOrder(resultMap.get("elementOrder").toString());
				popedom.setElementTitle(resultMap.get("elementTitle").toString());
				popedom.setElementUrl(resultMap.get("elementUrl").toString());
				popedom.setPElementId(resultMap.get("pElementId").toString());
				popedomList.add(popedom);
				
			}
			
			
		
			session.closeSession();
		} catch (Exception e) {
			if (session != null) {
				session.exceptionCloseSession();
			}
			throw new CquptException(e);
		}
	
		return popedomList;
	}
	
	/**
	 * 增加一个权限
	 * @param popedom
	 * @throws DataStormException 
	 */
	/*public String addPopedom(Popedom popedom) throws CquptException {
	
		String elementId = "-1";
		DataStormSession session = null;
		try {
			DataStormSessionFactory factory = new DataStormSessionFactory();
			session = factory.getDataStormSession();
			KeyGenerator keyGenerator = KeyGenerator.getInstance("element_seq", session);
			elementId = keyGenerator.getNextKey() + "";
			popedom.setElementId(this.objChanger.strToNum(elementId));
			
			session.add(popedom);
			session.closeSession();
		} catch (Exception e) {
			if (session != null) {
				session.exceptionCloseSession();
			}
			throw new CquptException(e);
		}
		
	
		return elementId;
	}*/
	
	/**
	 * 修改某一权限
	 * @param popedom
	 * @throws DataStormException
	 */
/*	public void updatePopedom(Popedom popedom) throws CquptException {
	
		DataStormSession session = null;
		try {
			DataStormSessionFactory factory = new DataStormSessionFactory();
			session = factory.getDataStormSession();
			session.update(popedom);
			session.closeSession();
		} catch (Exception e) {
			if (session != null) {
				session.exceptionCloseSession();
			}
			throw new CquptException(e);
		}
		
		
	}
	*/
	/**
	 * 删除权限
	 * @param popedom
	 * @throws DataStormException
	 */
	/*public void delPopedom(Popedom popedom) throws CquptException {
		
		DataStormSession session = null;
		try {
			DataStormSessionFactory factory = new DataStormSessionFactory();
			session = factory.getDataStormSession();
			session.remove(popedom);
			session.closeSession();
		} catch (Exception e) {
			if (session != null) {
				session.exceptionCloseSession();
			}
			throw new CquptException(e);
		}
		
	
	}
	*/
	 public List initPopedomGroup(String elementId) throws CquptException{
		 List popedomList = this.showPopedom(elementId);
			System.out.println("pppppppppppppppppp");
			List popedomGroup = new ArrayList();
			System.out.println("pppppddddddddddddddpp");
			DataStormSession session = null;
			try {
				session = DataStormSession.getInstance();
				String sql = "select t.menuid ELEMENT_ID,t.menuname ELEMENT_NAME,t.url ELEMENT_URL,t.menuleave ELEMENT_GRADE ,t.p_menuid P_ELEMENT_ID,t.meun_title ELEMENT_TITLE,t.meun_desc ELEMENT_DESC,t.menu_order ELEMENT_ORDER  from sys_menu t ORDER BY t.menuleave,t.menu_order ";
				List resultList = session.findSql(sql);
				System.out.println(sql);
				Map resultMap;
		
				
				for(int i = 0; i<resultList.size(); i++) {
					resultMap = (Map)resultList.get(i);
					Popedom popedom = new Popedom();
					popedom.setElementDesc(resultMap.get("elementDesc").toString());
					popedom.setElementGrade(resultMap.get("elementGrade").toString());
					popedom.setElementId(resultMap.get("elementId").toString());
					popedom.setElementName(resultMap.get("elementName").toString());
					popedom.setElementOrder(resultMap.get("elementOrder").toString());
					popedom.setElementTitle(resultMap.get("elementTitle").toString());
					popedom.setElementUrl(resultMap.get("elementUrl").toString());
					popedom.setPElementId(resultMap.get("pElementId").toString());
					popedomList.add(popedom);
					
				}
				
				
				
				if(this.objCensor.checkObjectIsNull(elementId)) {
					popedomGroup = this.divPopedomGroup(popedomList);//获取所有一级节点
				} else {
					Popedom popedom = null;
					for(int i=0; i<popedomList.size(); i++) {//找到当前elementId对应的POPDOM对象
						popedom = (Popedom) popedomList.get(i);
						if(popedom.getElementId().toString().equals(elementId)) {
							break;
						}
					}
					
					if("3".equals(popedom.getElementGrade().trim())) {
					
						popedomGroup = this.getThreeNodeGroup(popedomList, popedom);
					} else if("2".equals(popedom.getElementGrade().trim())) {
					
						popedomGroup = this.initTwoNodeGroup(popedomList, popedom);
					} //else {
						//throw new CquptException("菜单级别不存在！", CquptExceptionCode.DBEXCODE);
					//}
				}
			
				session.closeSession();
			} catch (Exception e) {
				if (session != null) {
					session.exceptionCloseSession();
				}
				throw new CquptException(e);
			}
		
			return popedomGroup;
	}
	/**
	 * 根据elementId取得一组权限
	 * @param elementId
	 * @return
	 * @throws DataStormException
	 */
	public List getPopedomGroup(String elementId) throws CquptException {
	
		List popedomList = this.showPopedom(elementId);
		System.out.println("pppppppppppppppppp");
		List popedomGroup = new ArrayList();
		System.out.println("pppppddddddddddddddpp");
		DataStormSession session = null;
		try {
			session = DataStormSession.getInstance();
			String sql = "select t.menuid ELEMENT_ID,t.menuname ELEMENT_NAME,t.url ELEMENT_URL,t.menuleave ELEMENT_GRADE ,t.p_menuid P_ELEMENT_ID,t.meun_title ELEMENT_TITLE,t.meun_desc ELEMENT_DESC,t.menu_order ELEMENT_ORDER  from sys_menu t ORDER BY t.menuleave,t.menu_order ";
			List resultList = session.findSql(sql);
			System.out.println(sql);
			Map resultMap;
	
			
			for(int i = 0; i<resultList.size(); i++) {
				resultMap = (Map)resultList.get(i);
				Popedom popedom = new Popedom();
				popedom.setElementDesc(resultMap.get("elementDesc").toString());
				popedom.setElementGrade(resultMap.get("elementGrade").toString());
				popedom.setElementId(resultMap.get("elementId").toString());
				popedom.setElementName(resultMap.get("elementName").toString());
				popedom.setElementOrder(resultMap.get("elementOrder").toString());
				popedom.setElementTitle(resultMap.get("elementTitle").toString());
				popedom.setElementUrl(resultMap.get("elementUrl").toString());
				popedom.setPElementId(resultMap.get("pElementId").toString());
				popedomList.add(popedom);
				
			}
			
			
			
			if(this.objCensor.checkObjectIsNull(elementId)) {
				popedomGroup = this.divPopedomGroup(popedomList);//获取所有一级节点
			} else {
				Popedom popedom = null;
				for(int i=0; i<popedomList.size(); i++) {//找到当前elementId对应的POPDOM对象
					popedom = (Popedom) popedomList.get(i);
					if(popedom.getElementId().toString().equals(elementId)) {
						break;
					}
				}
				
				if("3".equals(popedom.getElementGrade().trim())) {
				
					popedomGroup = this.getThreeNodeGroup(popedomList, popedom);
				} else if("2".equals(popedom.getElementGrade().trim())) {
				
					popedomGroup = this.getTwoNodeGroup(popedomList, popedom);
				} else if("1".equals(popedom.getElementGrade().trim())) {
				
					popedomGroup = this.getOneNodeGroup(popedomList, popedom);
				} else {
					throw new CquptException("菜单级别不存在！", CquptExceptionCode.DBEXCODE);
				}
			}
		
			session.closeSession();
		} catch (Exception e) {
			if (session != null) {
				session.exceptionCloseSession();
			}
			throw new CquptException(e);
		}
	
		return popedomGroup;
	}
	
	/**
	 * 给权限分组
	 * @param popedomList
	 * @return
	 */
	private List divPopedomGroup(List popedomList) {
		
		List popedomGroupList = new ArrayList();
		
		for(int i=0; i<popedomList.size(); i++) {
			Popedom popedom = (Popedom) popedomList.get(i);
			String grade = popedom.getElementGrade();
			if("1".equals(grade.trim())) {
				System.out.println("divPopedomGrouptest:"+popedom.getPElementId());
				popedomGroupList.addAll(this.getOneNodeGroup(popedomList, popedom));
			}
		}
	
		return popedomGroupList;
	}
	
	/**
	 * 取得三级节点组
	 * @param popedomList
	 * @param popedom
	 * @return
	 * @throws DataStormException 
	 */
	private List getThreeNodeGroup(List popedomList, Popedom popedom) throws CquptException {
	
		List popedomGroup = new ArrayList();
		Popedom twoPopedom = null;
		//取得二级节点
		for(int i=0; i<popedomList.size(); i++) {
			twoPopedom = (Popedom) popedomList.get(i);//找到该3级菜单的对应2级菜单
			String grade = twoPopedom.getElementGrade();
			if("2".equals(grade.trim()) && (twoPopedom.getElementId()).equals(popedom.getPElementId())) {
				break;
			}
		}
		if(this.objCensor.checkObjectIsNull(twoPopedom)) {
			throw new CquptException("二级节点不存在！", CquptExceptionCode.DBEXCODE);
		}
		
		//取得一级节点
		for(int i=0; i<popedomList.size(); i++) {
			Popedom onePopedom = (Popedom) popedomList.get(i);
			String grade = onePopedom.getElementGrade();
		
			if("1".equals(grade.trim()) && (onePopedom.getElementId()).equals(twoPopedom.getPElementId())) {
				popedomGroup.add(onePopedom);
				break;
			}
		}
		popedomGroup.add(twoPopedom);
		popedomGroup.add(popedom);
		return popedomGroup;
	}
	
	/**
	 * 取得二级节点组
	 * @param popedomList
	 * @param popedom
	 * @return
	 */
	private List initTwoNodeGroup(List popedomList, Popedom popedom){
		List popedomGroup = new ArrayList();
		//取得一级节点
		for(int i=0; i<popedomList.size(); i++) {
			Popedom onePopedom = (Popedom) popedomList.get(i);
			String grade = onePopedom.getElementGrade();
			if("1".equals(grade.trim()) && onePopedom.getElementId().equals(popedom.getPElementId())) {
				popedomGroup.add(onePopedom);
			}
		}
		popedomGroup.add(popedom);
		//取得三级节点
		return popedomGroup;
		
	}
	private List getTwoNodeGroup(List popedomList, Popedom popedom) {
	
		List popedomGroup = new ArrayList();
		//取得一级节点
		for(int i=0; i<popedomList.size(); i++) {
			Popedom onePopedom = (Popedom) popedomList.get(i);
			String grade = onePopedom.getElementGrade();
			if("1".equals(grade.trim()) && onePopedom.getElementId().equals(popedom.getPElementId())) {
				popedomGroup.add(onePopedom);
			}
		}
		popedomGroup.add(popedom);
		//取得三级节点
		for(int i=0; i<popedomList.size(); i++) {
			Popedom threePopedom = (Popedom) popedomList.get(i);
			String grade = threePopedom.getElementGrade();
			if("3".equals(grade.trim()) && (popedom.getElementId()).equals(threePopedom.getPElementId())) {
				popedomGroup.add(threePopedom);
			}
		}
	
		return popedomGroup;
	}
	
	/**
	 * 取得一级菜单组
	 * @param popedomList
	 * @param popedom
	 * @return
	 */
	private List getOneNodeGroup(List popedomList, Popedom popedom) {
	
		List popedomGroup = new ArrayList();
		popedomGroup.add(popedom);
		//取得二级节点
		for(int i=0; i<popedomList.size(); i++) {
			Popedom twoPopedom = (Popedom) popedomList.get(i);
			String twoGrade = twoPopedom.getElementGrade();
			if("2".equals(twoGrade.trim()) && (popedom.getElementId()).equals(twoPopedom.getPElementId())) {
				popedomGroup.add(twoPopedom);
				
				for(int ii=0; ii<popedomList.size(); ii++) {
					Popedom threePopedom = (Popedom) popedomList.get(ii);
					String threeGrade = threePopedom.getElementGrade();
					if("3".equals(threeGrade.trim()) && (twoPopedom.getElementId()).equals(threePopedom.getPElementId())) {
						popedomGroup.add(threePopedom);
					}
				}
			}
		}
		
	
		return popedomGroup;
	}
}
