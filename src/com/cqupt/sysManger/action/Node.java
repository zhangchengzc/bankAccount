package com.cqupt.sysManger.action;


public class Node {  
 
 public String id;  
 public String text;  
 public String parentId;  
 private Children children = new Children();  
   
 public String toString() {    
  String result = "{ id: '"+id+"',  text : '"+text+"'";  
    
  if (children != null && children.getSize() != 0) {  
   result += ", children : " + children.toString();  
  } else {  
   result += "";  
  }  
      
  return result + "}";  
 }  
   
   
 // ��Ӻ��ӽڵ�  
 public void addChild(Node node) {  
  this.children.addChild(node);  
 }  
}  
