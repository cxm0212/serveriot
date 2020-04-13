package com.cxm.iotserver.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.iot.api.message.entity.Message;
import com.cxm.iotserver.util.PrintWriterUtil;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * create by
 * 三和智控: cxm on 2020/4/2
 */
@ServerEndpoint("/ws")
@Component
public class WebSocketServer {

    // 静态变量，用来记录当前在线连接数,应该把它设计成线程安全的
    private static int onlineCount = 0;
    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象，若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketServer> webSockets = new CopyOnWriteArraySet<WebSocketServer>();
    private Session session;

    //设备所在的session集合
    private static ConcurrentHashMap<String, List<Session>> deviceSession = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Session,String> sessionDevice = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
        addOnlineCount(); // 在线数加1
        System.out.println("【websocket消息】有新的连接，当前总数为：" + webSockets.size());





//        Message message = new Message();
//        message.setMessageId("1245531148365554688");
//        JSONObject object = new JSONObject();
//        JSONObject object1 = new JSONObject();
//        object1.put("value",3);
//        object1.put("time","1585792741274");
//        object.put("OutputCurrent",object1);
//
//        object1 = new JSONObject();
//        object1.put("value",1);
//        object1.put("time","1585792741274");
//        object.put("PowerStatus",object1);
//
//        object1 = new JSONObject();
//        object1.put("value",21);
//        object1.put("time","1585792741274");
//        object.put("CurrentTemperature",object1);
//
//        object1 = new JSONObject();
//        object1.put("value",2000);
//        object1.put("time","1585792741274");
//        object.put("OutputVoltage",object1);
//
//        JSONObject object2 = new JSONObject();
//        object2.put("items",object);
//        object2.put("deviceName","3pIi0cGStaWEgcBqYCUd");
//        object2.put("iotId","3pIi0cGStaWEgcBqYCUd000100");
//
//
//        message.setPayload(JSON.toJSONString(object2).getBytes());
//        sendMessage(message);
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        subOnlineCount(); // 在线数减1
        System.out.println("【websocket消息】连接断开，当前总数为：" + webSockets.size());

        if(sessionDevice.get(session)!=null && deviceSession.get(sessionDevice.get(session))!=null){
            deviceSession.get(sessionDevice.get(session)).remove(session);
        }
        sessionDevice.remove(session);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("【websocket消息】收到消息：" + message);
        JSONObject object = JSONObject.parseObject(message);
        int type = object.getInteger("type");
        if(type == 1){//切换设备请求
            String iotId = object.getString("iotId");
            List<Session> sessions = new ArrayList<>();
            if(deviceSession.get(iotId)!=null && deviceSession.get(iotId).size()>0){
                sessions = deviceSession.get(iotId);
            }
            if(!sessions.contains(session)){
                sessions.add(session);
                deviceSession.put(iotId,sessions);
            }
            sessionDevice.put(session,iotId);
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("【websocket发生错误】：" + error);
        error.printStackTrace();
    }

    /**
     * 服务器主动推送消息
     * @param
     */
    public static void sendMessage(ResultData data) {
        try {
            String iotId = data.getIotId();
            List<Session> sessions = null;
            if(deviceSession.get(iotId)!=null){
                sessions = deviceSession.get(iotId);
                for (Session session : sessions) {
                    if(iotId.equals(sessionDevice.get(session)) && session.isOpen()){
                        session.getBasicRemote().sendText(PrintWriterUtil.objToJsonStr(data,null));
                        System.out.println("向session上报数据 : "+ session);
                        Thread.sleep((long) 0.01);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }




}
