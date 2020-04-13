package com.cxm.iotserver.controller;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cxm.iot.api.sdk.openapi.DeviceManager;
import com.aliyuncs.iot.model.v20180120.*;
import com.cxm.iot.api.sdk.openapi.ProductManager;
import com.cxm.iot.api.sdk.openapi.ThingModelManager;
import com.cxm.iotserver.server.TopicHandler;
import com.cxm.iotserver.util.PrintWriterUtil;
import com.cxm.iotserver.websocket.datadto.DevicePropertyDTO;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * create by
 * 三和智控: cxm on 2020/3/19
 */
@Controller
@Log
public class DeviceController {

    static String productKey = "a1zMD8aob6U";
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @CrossOrigin
    @RequestMapping(value = "/finddevicelistbyproduct")
    public void findDeviceListByProduct(HttpServletResponse resp, HttpServletRequest res){
        String productKey = res.getParameter("productKey");
        List<QueryDeviceResponse.DeviceInfo> deviceAllList = new ArrayList<>();

        List<QueryDeviceResponse.DeviceInfo> deviceList=null;
        if(null==productKey || productKey==""){
            QueryProductListResponse.Data data = ProductManager.queryProductListTest(1,100,null);
            List<QueryProductListResponse.Data.ProductInfo> list = data.getList();
            for (QueryProductListResponse.Data.ProductInfo product : list) {
                productKey = product.getProductKey();
                deviceList = DeviceManager.queryDevice(productKey, 50, 1);
                deviceAllList.addAll(deviceList);
            }
        }else{
            deviceAllList = DeviceManager.queryDevice(productKey, 50, 1);
        }


        JSONObject json = new JSONObject();
        json.put("rows", deviceAllList);
//        PrintWriterUtil.writerJSON(resp,json,null);
        PrintWriterUtil.writerJSON(resp,deviceAllList,null);
    }

    @CrossOrigin
    @RequestMapping(value = "/getdevicedetail")
    public void test(HttpServletResponse resp, HttpServletRequest res){
        String iotId = res.getParameter("iotId");
        String productKey = res.getParameter("productKey");
        String deviceName = res.getParameter("deviceName");




        QueryDeviceDetailResponse.Data data = DeviceManager.queryDeviceDetail(iotId, productKey, deviceName);
        JSONArray deviceArray=new JSONArray();
        deviceArray.add(data);
        JSONObject json = new JSONObject();
        json.put("rows", deviceArray);
//        System.out.println(json);
        try {
//            resp.getWriter().write(JSON.toJSONString(json));
            resp.getWriter().write(JSON.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @CrossOrigin
    @RequestMapping(value = "/queryDevicePropertiesData")
    public void queryDevicePropertiesData(HttpServletRequest res,HttpServletResponse response){
        String iotId = res.getParameter("iotId");
        String productKey = res.getParameter("productKey");
        String deviceName = res.getParameter("deviceName");

        List<String> identifiers = new ArrayList<>();
        identifiers.add("OutputVoltage");
        identifiers.add("OutputCurrent");
        identifiers.add("CurrentTemperature");
        identifiers.add("PowerStatus");

        Long starttime = null;
        Long endtime = null;
        endtime = new Date().getTime();
        starttime = endtime-(1000*60*60*24*10);
        int asc = 0;//倒序


        QueryDevicePropertyDataResponse.Data outputVoltage = ThingModelManager.queryDevicePropertyData(iotId,
                productKey, deviceName, "OutputVoltage", starttime, endtime, 1, asc);
        QueryDevicePropertyDataResponse.Data outputCurrent = ThingModelManager.queryDevicePropertyData(iotId,
                productKey, deviceName, "OutputCurrent", starttime, endtime, 1, asc);
        QueryDevicePropertyDataResponse.Data currentTemperature = ThingModelManager.queryDevicePropertyData(iotId,
                productKey, deviceName, "CurrentTemperature", starttime, endtime, 1, asc);
        QueryDevicePropertyDataResponse.Data powerStatus = ThingModelManager.queryDevicePropertyData(iotId,
                productKey, deviceName, "PowerStatus", starttime, endtime, 1, asc);


        List<QueryDevicePropertyDataResponse.Data.PropertyInfo> list = outputVoltage.getList();
        List<QueryDevicePropertyDataResponse.Data.PropertyInfo> list2 = outputCurrent.getList();
        List<QueryDevicePropertyDataResponse.Data.PropertyInfo> list3 = currentTemperature.getList();
        List<QueryDevicePropertyDataResponse.Data.PropertyInfo> list4 = powerStatus.getList();

        DevicePropertyDTO data = new DevicePropertyDTO();
        if(list.size()>0){
            data.setOutputVoltage(list.get(0).getValue());
            data.setVoltageTime(TopicHandler.paseLongDate(list.get(0).getTime()));
        }
        if(list2.size()>0){
            data.setOutputElectricity(list2.get(0).getValue());
            data.setElectricityTime(TopicHandler.paseLongDate(list2.get(0).getTime()));
        }
        if(list3.size()>0){
            data.setCurrentTemperature(list3.get(0).getValue());
            data.setTemperatureTime(TopicHandler.paseLongDate(list3.get(0).getTime()));
        }
        if(list4.size()>0){
            data.setPowerStatus(list4.get(0).getValue());
            data.setPowerTime(TopicHandler.paseLongDate(list4.get(0).getTime()));
        }

        PrintWriterUtil.writerJSON(response,data,null);

    }

    @CrossOrigin
    @RequestMapping(value = "/querydevicepropertyhistorydata")
    public void queryDevicePropertyHistoryData(HttpServletRequest res,HttpServletResponse response){
        String iotId = res.getParameter("iotId");
        String productKey = res.getParameter("productKey");
        String deviceName = res.getParameter("deviceName");
        String identifiers = res.getParameter("identifier");
        String[] identifier = res.getParameter("identifier").split(",");
        int asc = Integer.parseInt(res.getParameter("asc"));
        int pageSize = Integer.parseInt(res.getParameter("pageSize"));
        Long starttime = null;
        Long endtime = null;
        try {
            starttime = sdf.parse(res.getParameter("startTime")).getTime();
            endtime = sdf.parse(res.getParameter("endTime")).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List identifierList = new ArrayList();
        JSONObject objData=new JSONObject();
        int size = 0;
        for (String str : identifier) {
            identifierList.add(str);

            QueryDevicePropertyDataResponse.Data data = ThingModelManager.queryDevicePropertyData(iotId,
                    productKey, deviceName, str, starttime, endtime, pageSize, asc);

            objData.put(str,data.getList());
            if(data.getList().size() > size){
                size = data.getList().size();
            }
        }
        List newList = new ArrayList();
        JSONObject newData=null;
        for (int i = 0; i < size; i++) {
            newData=new JSONObject();
            newData.put("key",i);
            for (String str : identifier) {
                List<QueryDevicePropertyDataResponse.Data.PropertyInfo> list = (List) objData.get(str);
                if(list.size()>i){
                    newData.put(str+"_value",list.get(i).getValue());
                    newData.put(str+"_time",TopicHandler.paseLongDate(list.get(i).getTime()));
                }
                continue;
            }
            newList.add(newData);
        }
//        List<QueryDevicePropertiesDataResponse.PropertyDataInfo> list = ThingModelManager.queryDevicePropertiesData(
//                iotId, productKey, deviceName, identifierList , endtime,starttime, pageSize, asc);


//        JSONObject objData=new JSONObject();
//        List newList = null;
//        JSONObject obj=null;
//        String iden=null;
//        List<QueryDevicePropertiesDataResponse.PropertyDataInfo.PropertyInfo> list1=null;
//        QueryDevicePropertiesDataResponse.PropertyDataInfo.PropertyInfo propertyInfo=null;
//
//        for (QueryDevicePropertiesDataResponse.PropertyDataInfo propertyDataInfo : list){
//            newList = new ArrayList();
//            iden = propertyDataInfo.getIdentifier();
//            list1 = propertyDataInfo.getList();
//            for (int i = 0; i < list1.size(); i++) {
//                obj = new JSONObject();
//                obj.put("key",i);
//                propertyInfo = list1.get(i);
//                obj.put("value",propertyInfo.getValue());
//                obj.put("time",sdf.format(new Date(propertyInfo.getTime())));
//                newList.add(obj);
//            }
//            objData.put(iden,newList);
//        }



        PrintWriterUtil.writerJSON(response, newList,null);

    }

    @CrossOrigin
    @RequestMapping(value = "setDeviceProperty")
    public void setDeviceProperty(HttpServletRequest request,HttpServletResponse response){
        String iotId = request.getParameter("iotId");
        String productKey = request.getParameter("productKey");
        String deviceName = request.getParameter("deviceName");
        String propertyItems = request.getParameter("propertyItems");

        JSONObject object = JSON.parseObject(propertyItems);
        Integer outputVoltage = object.getInteger("OutputVoltage");
        ThingModelManager.setDeviceProperty(iotId,productKey,deviceName,propertyItems);
    }

    public static void main(String[] args) {

        JSONObject obj = new JSONObject();
        obj.put("OutputVoltage",25);

//        byte[] encode = Base64.getEncoder().encode(JSON.toJSONString(obj).getBytes());
//        MessageBrokerManager.pub("a1ppUE8HllR","/a1ppUE8HllR/3pIi0cGStaWEgcBqYCUd/user/update",new String(encode),1);


        ThingModelManager.setDeviceProperty("3pIi0cGStaWEgcBqYCUd000100","a1ppUE8HllR","3pIi0cGStaWEgcBqYCUd",JSON.toJSONString(obj));
//        String encode = Base64.getEncoder().encodeToString("dGhpcyBpcyBhbiBleGFtcGxl".getBytes());
//        System.out.println(encode);
//        byte[] s = Base64.getDecoder().decode("测试数据".getBytes());
//        System.out.println(Base64.getEncoder().encodeToString(s));

//        List deviceAllList = DeviceManager.queryDevice("a1ppUE8HllR", 50, 1);

//        DeviceManager.queryDeviceDetail("3pIi0cGStaWEgcBqYCUd000100", "a1ppUE8HllR", "3pIi0cGStaWEgcBqYCUd");


//        long l1 = new Date().getTime();
//        long l = l1 - (1000 * 60 * 60 * 24 * 10);
//        System.out.println( l1 +"_---------------"+ l);
//
//        System.out.println(sdf.format(new Date(l1)));
//        System.out.println(sdf.format(new Date(l)));
//
//        List<String> identifiers = new ArrayList<>();
//        identifiers.add("OutputVoltage");
//        identifiers.add("OutputCurrent");
//        identifiers.add("CurrentTemperature");
//        identifiers.add("PowerStatus");
//        Long starttime = null;
//        Long endtime = null;
//        try {
//            starttime = sdf.parse("2020-04-02 08:00:00").getTime();
//            endtime = sdf.parse("2020-04-02 13:00:00").getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        System.out.println(sdf.format(new Date()));
//        System.out.println(starttime +"---------"+endtime);
//        ThingModelManager.queryDevicePropertyData("3pIi0cGStaWEgcBqYCUd000100","a1ppUE8HllR", "3pIi0cGStaWEgcBqYCUd",
//                "OutputVoltage",starttime,endtime,20,1);
//        ThingModelManager.queryDevicePropertiesData("3pIi0cGStaWEgcBqYCUd000100","a1ppUE8HllR", "3pIi0cGStaWEgcBqYCUd",
//                identifiers,starttime,endtime,20,1);
    }
}
