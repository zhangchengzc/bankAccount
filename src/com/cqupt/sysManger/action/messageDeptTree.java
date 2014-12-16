package com.cqupt.sysManger.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class messageDeptTree extends ActionSupport {
	HttpServletResponse response = null;

	public String execute() {
		// json ��ʽ[{text:'�ڵ�һ',children:[{text:'�ڵ�1.1'}]},{text:'�ڵ��'}]
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
System.out.println("EmailDeptTree");
		List dataList = null;
		try {
			dataList = getResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		HashMap nodeList = new HashMap();
		// һ���ڵ�
		Node root = new Node();

		for (Iterator it = dataList.iterator(); it.hasNext();) {
			Map dataRecord = (Map) it.next();
			Node node = new Node();
			node.id = (String) dataRecord.get("treeId");
			node.text = (String) dataRecord.get("deptName");
			node.parentId = (String) dataRecord.get("parentId");
			nodeList.put(node.id, node);
		}
		System.out.println("nodeList"+nodeList);
		Set entrySet = nodeList.entrySet();
		System.out.println("set"+entrySet);
		for (Iterator it = entrySet.iterator(); it.hasNext();) {
			Node node = (Node) ((Map.Entry) it.next()).getValue();
			System.out.println("node:"+node);
			if (node.parentId.equals("00")) {
				root = node;
			} else {
				((Node) nodeList.get(node.parentId)).addChild(node);
			}
		}
//		Iterator it = root.iterator();
//		String json = "";
//		while (it.hasNext()) {
//			json += it.next().toString() + ",";
//		}
//		json = "[" + json.substring(0, json.length() - 1) + "]";
		
		System.out.println("����11111111111˵"+root.toString());
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print("["+root.toString()+"]");
		out.flush();
		out.close();
		return null;
	}

	private List getResult() {
		String sql = "select dept_id as tree_id,dept_name,parent_dept_id as parent_id from sys_dept ";
		System.out.println("������"+sql);
		List list = null;
	 try{
			DataStormSession session = DataStormSession.getInstance();
			Map map = session.executeSQL(sql);
			System.out.println("���������ؽ��"+map);
			if(map!=null){
				list = (List) map.get("resultList");
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	   return list;  
	}
}
