/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.common.websocket.server;


import javax.websocket.Session;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ChatSessionPool {

	private static final Map<Session,String> userconnections = new HashMap<Session,String>();
	
	/**
	 * 获取用户名
	 * @param session
	 */
	public static String getUserByKey(Session conn){
		return userconnections.get(conn);
	}
	
	/**
	 * 获取Session
	 * @param user
	 */
	public static Session getSessionByUser(String user){
		Set<Session> keySet = userconnections.keySet();
		synchronized (keySet) {
			for (Session conn : keySet) {
				String cuser = userconnections.get(conn);
				if(cuser.equals(user)){
					return conn;
				}
			}
		}
		return null;
	}
	
	/**
	 * 向连接池中添加连接
	 * @param inbound
	 */
	public static void addUser(String user, Session conn){
		userconnections.put(conn,user);	//添加连接
	}
	
	/**
	 * 获取所有的在线用户
	 * @return
	 */
	public static Collection<String> getOnlineUser(){
//		List<String> setUsers = new ArrayList<String>();
		Collection<String> setUsers = userconnections.values();
//		for(String u:setUser){
//			setUsers.add("<a onclick=\"toUserMsg('"+u+"');\">"+u+"</a>");
//		}
		return setUsers;
	}
	
	/**
	 * 移除连接池中的连接
	 * @param inbound
	 */
	public static boolean removeUser(Session conn){
		if(userconnections.containsKey(conn)){
			userconnections.remove(conn);	//移除连接
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 向特定的用户发送数据
	 * @param user
	 * @param message
	 */
	public static void sendMessageToUser(Session conn,String message){
		if(null != conn && null != userconnections.get(conn)){
			conn.getAsyncRemote().sendText(message);
		}
	}
	
	/**
	 * 向所有的用户发送消息
	 * @param message
	 */
	public static void sendMessage(String message){
		Set<Session> keySet = userconnections.keySet();
		synchronized (keySet) {
			for (Session conn : keySet) {
				String user = userconnections.get(conn);
				if(user != null){
					conn.getAsyncRemote().sendText(message);
				}
			}
		}
	}
}
