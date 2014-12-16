package com.cqupt.sysManger.action;

import java.util.*;

public class Children {  
 private List list = new ArrayList();  
   
 public int getSize() {  
  return list.size();  
 }  
   
 public void addChild(Node node) {  
  list.add(node);  
 }  
   
 // ƴ�Ӻ��ӽڵ��JSON�ַ�  
 public String toString() {  
  String result = "[";    
  for (Iterator it = list.iterator(); it.hasNext();) {  
   result += ((Node) it.next()).toString();  
   result += ",";  
  }  
  result = result.substring(0, result.length() - 1);  
  result += "]";  
  return result;  
 }  
   
}