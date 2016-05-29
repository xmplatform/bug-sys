package cn.gx.common.websocket.utils;


import cn.gx.common.websocket.client.SocketClient;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *  封装了链接 websocket 客户端的代码
 */
public class WebSocketUtil {


    private static SocketClient client;

    private static final String webHostAddress = "ws://localhost:8080/";// 可以 contextlistener 是后配置

    private  static void initializeWebSocket(String webSocketAddress) throws URISyntaxException {
        System.out.println("REST service: open websocket client at " + webSocketAddress);
        client = new SocketClient(new URI(webHostAddress+webSocketAddress));
    }

    public static void sendMessage2WebSocket(String webSocketAddress, String message) {
        if (client == null) {
            try {
                initializeWebSocket(webSocketAddress);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        // push 消息
        client.sendMessage(message);
    }
}
