package cn.gx.common.websocket.client;

import javax.websocket.*;
import java.net.URI;

/**
 * Created by guanxine on 2016/5/29.
 */
public class SocketClient {


    // 与 websocket server 建立链接
    public SocketClient(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Session userSession = null;

    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("client: opening websocket ");//1
        this.userSession = userSession;//1.2
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("client: closing websocket");
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("client: received message "+message);//3->4,6->7
    }


    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);//3
    }
}
