package com.cxm.iotserver.server;

import com.aliyun.openservices.iot.api.message.api.MessageClient;
import com.aliyun.openservices.iot.api.message.callback.MessageCallback;
import com.aliyun.openservices.iot.api.message.entity.Message;
import com.cxm.iot.api.sdk.openapi.AbstractManager;
import com.cxm.iotserver.websocket.datadto.CommonDataDTO;
import com.cxm.iotserver.websocket.datadto.DevicePropertyDTO;
import com.cxm.iotserver.websocket.WebSocketServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * create by
 * 三和智控: cxm on 2020/4/1
 */
@Component
public class SubscriptionMessage implements ApplicationRunner {

    protected static Queue<Object> sendDataQueue = new LinkedList();
    private static MessageClient messageClient = AbstractManager.messageClient;


    /**
     * 监听设备上报数据
     */
    public static void startServerListen() {
        messageClient.connect(messageToken -> {
            Message m = messageToken.getMessage();
            String topic = m.getTopic();
            TopicHandler.handler(topic,m);
            return MessageCallback.Action.CommitSuccess;
        });

    }

    /**
     * 处理数据上报至浏览器用户
     */
    public static void sendQueue() {


        while (true){
            CommonDataDTO data = (CommonDataDTO) sendDataQueue.poll();
            if(data!=null)WebSocketServer.sendMessage(data);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 在这里处理您收到消息后的具体业务逻辑。
     */
    private static void processMessage(Message message) {
        String m = "Message{payload={\"deviceType\":\"CustomCategory\",\"iotId\":\"3pIi0cGStaWEgcBqYCUd000100\",\"requestId\":\"123\",\"productKey\":\"a1ppUE8HllR\",\"gmtCreate\":1585792741260,\"deviceName\":\"3pIi0cGStaWEgcBqYCUd\",\"items\":{\"OutputVoltage\":{\"value\":2000,\"time\":1585792741274},\"CurrentTemperature\":{\"value\":21,\"time\":1585792741274},\"PowerStatus\":{\"value\":1,\"time\":1585792741274},\"OutputCurrent\":{\"value\":3,\"time\":1585792741274}}}, \"topic\"=\"/a1ppUE8HllR/3pIi0cGStaWEgcBqYCUd/thing/event/property/post\", \"messageId\"=\"1245531148365554688\", qos=1, generateTime=1585792741277}";
//        WebSocketServer.sendMessage(message);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        startServerListen();
        sendQueue();
    }
}

