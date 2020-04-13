package com.cxm.iotserver.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.iot.api.message.entity.Message;
import com.cxm.iotserver.websocket.ResultData;
import com.cxm.iotserver.websocket.WebSocketServer;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * create by
 * 三和智控: cxm on 2020/4/3
 */
public class TopicHandler {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void handler(String topic, Message message){
        if(topic.startsWith("/as/mqtt/status")){
            new DeviceStatusHandler(message);
        }else if(topic.endsWith("/thing/event/property/post")){
            new DevicePropertyHandler(message);
        }
    }

    public static String paseLongDate(String str){
        long lo = Long.parseLong(str);
        return sdf.format(new Date(lo));
    }

    public void s(){

    }

}



class DevicePropertyHandler{
    private Message message;
    public DevicePropertyHandler(Message message) {
        this.message = message;
        paseMessage();
    }
    public void paseMessage(){
        System.out.println("设备属性上报 : " + message);
        ResultData data = new ResultData();
        JSONObject connectJson = JSON.parseObject(new String(message.getPayload()));

        String iotId = connectJson.getString("iotId");
        JSONObject items = connectJson.getJSONObject("items");

        data.setIotId(iotId);
        if(items.getJSONObject("OutputVoltage")!=null){
            JSONObject outputVoltage = items.getJSONObject("OutputVoltage");
            data.setOutputVoltage(outputVoltage.getString("value"));
            data.setVoltageTime(TopicHandler.paseLongDate(outputVoltage.getString("time")));
        }
        if(items.getJSONObject("CurrentTemperature")!=null){
            JSONObject currentTemperature = items.getJSONObject("CurrentTemperature");
            data.setCurrentTemperature(currentTemperature.getString("value"));
            data.setTemperatureTime(TopicHandler.paseLongDate(currentTemperature.getString("time")));
        }
        if(items.getJSONObject("PowerStatus")!=null){
            JSONObject powerStatus = items.getJSONObject("PowerStatus");
            data.setPowerStatus(powerStatus.getString("value"));
            data.setPowerTime(TopicHandler.paseLongDate(powerStatus.getString("time")));
        }
        if(items.getJSONObject("OutputCurrent")!=null){
            JSONObject outputCurrent = items.getJSONObject("OutputCurrent");
            data.setOutputElectricity(outputCurrent.getString("value"));
            data.setElectricityTime(TopicHandler.paseLongDate(outputCurrent.getString("time")));
        }

        SubscriptionMessage.sendDataQueue.offer(data);
//        WebSocketServer.sendMessage(iotId,data);
    }
}

class DeviceStatusHandler{
    private Message message;
    public DeviceStatusHandler(Message message) {
        this.message = message;
        paseMessage();
    }
    public void paseMessage(){
        System.out.println("设备上下线状态上报 : " + message);
//        ResultData data = new ResultData();
//        JSONObject connectJson = JSON.parseObject(new String(message.getPayload()));
//
//        String iotId = connectJson.getString("iotId");
//        JSONObject items = connectJson.getJSONObject("items");


//        WebSocketServer.sendMessage(iotId,data);
    }
}
