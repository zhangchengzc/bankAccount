package com.cqupt.login;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineUser implements HttpSessionListener,HttpSessionAttributeListener  {
	String id = null;
	String username = null;
	public static Map<String,HttpSession> online = new HashMap< String,HttpSession>();	
	
	public synchronized void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		id = session.getId();
System.out.println(id+"来了");
	}

	public synchronized void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();		
		//if(session != null){
			username = (String)session.getAttribute("userId");
			online.remove(username);

System.out.println(username + " 下线了");
			session.removeAttribute("userId");
			session.removeAttribute("userIp");
			session.removeAttribute("loginTime");
			session.removeAttribute("userName");
			session.removeAttribute("userDept");
			session.removeAttribute("deptId");
			session.removeAttribute("groupId");
			session.removeAttribute("dataAuthId");
			
		/*	session.setAttribute("userId", userId);//
			session.setAttribute("userIp", addr);//
			session.setAttribute("loginTime", loginTime);//
			session.setAttribute("userName", getUserName());
			session.setAttribute("userDept", getUserDept());
			session.setAttribute("deptId", getUserDeptId());
			session.setAttribute("rlId", getRlId());
			session.setAttribute("dataAuthId", getDataAuthId());*/
			
			
			
			session.invalidate();
			showOnlineUsers();
		//}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void showOnlineUsers(){
		Iterator it = online.keySet().iterator();
		while(it.hasNext()){
			String userID = (String)it.next();
			System.out.println("userID:"+userID);
			System.out.println("IP:"+(((HttpSession)OnlineUser.online.get(userID))).getAttribute("userIp"));
			System.out.println("loginTime:"+(((HttpSession)OnlineUser.online.get(userID))).getAttribute("loginTime"));
		}
System.out.println("当前在线人数： "+ online.size());


	}
	
}