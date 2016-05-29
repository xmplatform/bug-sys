package cn.gx.common.websocket.server;

import cn.gx.common.json.AjaxJson;
import cn.gx.common.utils.SpringContextHolder;
import cn.gx.common.websocket.utils.Constant;
import cn.gx.modules.iim.entity.ChatHistory;
import cn.gx.modules.iim.service.ChatHistoryService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by guanxine on 2016/5/29.
 */

@ServerEndpoint("/chatSocket")
public class SocketServer {


    public SocketServer(){

        System.out.println("socket server init");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        message = message.toString();
        ChatHistoryService chatHistoryService = SpringContextHolder.getBean("chatHistoryService");
        // TODO Auto-generated catch block
        if(null != message && message.startsWith(Constant._online_user_)){//用户上线
            String userId = message.replaceFirst(Constant._online_user_, "");
            this.userjoin(userId,session);

            //通知所有用户更新在线信息
            Collection<String> onlineUsers = ChatSessionPool.getOnlineUser();
            AjaxJson j = new AjaxJson();
            j.put("data", onlineUsers);
            ChatSessionPool.sendMessage("_online_all_status_"+j.getJsonStr());//通知所有用户更新在线信息

            //读取离线信息
            ChatHistory chat = new ChatHistory();
            chat.setUserid2(userId);
            chat.setStatus("0");
            List<ChatHistory> list =chatHistoryService.findList(chat);
            for(ChatHistory c : list){
                ChatSessionPool.sendMessageToUser(session,  c.getUserid1()+Constant._msg_+c.getUserid2()+Constant._msg_+c.getMsg()+Constant._msg_+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getCreateDate()));//向所某用户发送消息
                c.setStatus("1");//标记为已读
                chatHistoryService.save(c);
            }


        }if(null != message && message.startsWith(Constant._leave_user_)){//用户离线
            this.userLeave(session);
            Collection<String> onlineUsers = ChatSessionPool.getOnlineUser();
            AjaxJson j = new AjaxJson();
            j.put("data", onlineUsers);
            ChatSessionPool.sendMessage("_online_all_status_"+j.getJsonStr());//通知所有用户更新在线信息
        }if(null != message && message.contains(Constant._msg_)){//
            String []arr = message.split(Constant._msg_);
            String fromUser = arr[0];
            String toUser = arr[1];
            String msg = arr[2];

            //保存聊天记录
            ChatHistory chat = new ChatHistory();
            chat.setUserid1(fromUser);
            chat.setUserid2(toUser);
            chat.setMsg(msg);

            chat.setCreateDate(new Date());


            Session toUserConn = ChatSessionPool.getSessionByUser(toUser);
            if(toUserConn != null){
                ChatSessionPool.sendMessageToUser(ChatSessionPool.getSessionByUser(toUser),message);//向所某用户发送消息
                chat.setStatus("1");//设置为已读
            }else{
                ChatSessionPool.sendMessageToUser(session, "_sys_对方现在离线，他将在上线后收到你的消息!");//同时向本人发送消息
                chat.setStatus("0");//设置为未读
            }

            chatHistoryService.save(chat);

        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("client-id") String clientId) {
        //		Collection<String> onlineUsers = MsgServerPool.getOnlineUser();
//		AjaxJson j = new AjaxJson();
//		j.put("data", onlineUsers);
//		MsgServerPool.sendMessageToUser(conn, "_online_all_status_"+j.getJsonStr());//首次登陆系统时，获取用户的在线状态
    }

    @OnClose
    public void onClose(Session session) {
        userLeave(session);
        Collection<String> onlineUsers = ChatSessionPool.getOnlineUser();
        AjaxJson j = new AjaxJson();
        j.put("data", onlineUsers);
        ChatSessionPool.sendMessage("_online_all_status_"+j.getJsonStr());//通知所有用户更新在线信息
    }



    /**
     * 用户加入处理
     * @param user
     */
    public void userjoin(String user, Session conn){
//		AjaxJson j = new AjaxJson();
//		j.put("type", "user_join");
//		j.put("user", "<a onclick=\"toUserMsg('"+user+"');\">"+user+"</a>");
//		MsgServerPool.sendMessage(j.getJsonStr());				//把当前用户加入到所有在线用户列表中
//		String joinMsg = "{\"from\":\"[系统]\",\"content\":\""+user+"上线了\",\"timestamp\":"+new Date().getTime()+",\"type\":\"message\"}";
//		MsgServerPool.sendMessage(joinMsg);						//向所有在线用户推送当前用户上线的消息
//		j = new AjaxJson();
//		j.put("type", "get_online_user");
        ChatSessionPool.addUser(user,conn);							//向连接池添加当前的连接对象
//		j.put("list", MsgServerPool.getOnlineUser());
//		MsgServerPool.sendMessageToUser(conn, j.getJsonStr());	//向当前连接发送当前在线用户的列表
    }
    /**
     * 用户下线处理
     * @param user
     */
    public void userLeave(Session conn){
        String user = ChatSessionPool.getUserByKey(conn);
        boolean b = ChatSessionPool.removeUser(conn);				//在连接池中移除连接
//		if(b){
//			AjaxJson j = new AjaxJson();
//			j.put("type", "user_leave");
//			j.put("user", "<a onclick=\"toUserMsg('"+user+"');\">"+user+"</a>");
//			MsgServerPool.sendMessage(j.getJsonStr());			//把当前用户从所有在线用户列表中删除
//			String joinMsg = "{\"from\":\"[系统]\",\"content\":\""+user+"下线了\",\"timestamp\":"+new Date().getTime()+",\"type\":\"message\"}";
//			MsgServerPool.sendMessage(joinMsg);					//向在线用户发送当前用户退出的消息
//		}
    }
}
