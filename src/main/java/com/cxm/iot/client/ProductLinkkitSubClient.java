package com.cxm.iot.client;

import com.aliyun.alink.apiclient.CommonRequest;
import com.aliyun.alink.apiclient.CommonResponse;
import com.aliyun.alink.apiclient.IoTCallback;
import com.aliyun.alink.apiclient.utils.StringUtils;
import com.aliyun.alink.dm.api.DeviceInfo;
import com.aliyun.alink.dm.model.ResponseModel;
import com.aliyun.alink.linkkit.api.IoTMqttClientConfig;
import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linkkit.api.LinkKitInitParams;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import static com.aliyun.alink.linksdk.tmp.utils.TmpConstant.TAG;

/**
 * create by
 * 三和智控: cxm on 2020/4/13
 */
public class ProductLinkkitSubClient {

    public static void main(String[] args) {
        //  #######  一型一密动态注册接口开始 ######
/**
 * 注意：动态注册成功，设备上线之后，不能再次执行动态注册，云端会返回已注册。
 */
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.productKey = "a1ppUE8HllR"; //必填
        deviceInfo.deviceName = "3pIi0cGStaWEgcBqYCUd"; //必填
        deviceInfo.productSecret = "asHLxHVjGDcjsbRh"; //必填

        LinkKitInitParams params = new LinkKitInitParams();
        IoTMqttClientConfig config = new IoTMqttClientConfig();
        config.productKey = deviceInfo.productKey;
        config.deviceName = deviceInfo.deviceName;

        params.mqttClientConfig = config;
        params.deviceInfo = deviceInfo;

        final CommonRequest request = new CommonRequest();
        request.setPath("/auth/register/device");

        LinkKit.getInstance().deviceRegister(params, request, new IoTCallback() {
            @Override
            public void onFailure(CommonRequest commonRequest, Exception e) {
                ALog.e(TAG, "动态注册失败 " + e);
            }

            @Override
            public void onResponse(CommonRequest commonRequest, CommonResponse commonResponse) {
                if (commonResponse == null || StringUtils.isEmptyString(commonResponse.getData())) {
//                    ALog.e(TAG, "动态注册失败 response=null");
                    System.out.println("动态注册失败 response=null");
                    return;
                }
                try {
                    ResponseModel<Map<String, String>> response = new Gson().fromJson(commonResponse.getData(), new TypeToken<ResponseModel<Map<String, String>>>() {
                    }.getType());
                    if (response != null && "200".equals(response.code)) {
//                        ALog.d(TAG, "动态注册成功" + (commonResponse == null ? "" : commonResponse.getData()));
                        /**  获取 deviceSecret, 存储到本地，然后执行初始化建联
                         * 这个流程只能走一次，获取到 secret 之后，下次启动需要读取本地存储的三元组，
                         * 直接执行初始化建联，不可以再走动态初始化
                         */

                        System.out.println(response.data+"-----------------------下发数据----------动态注册成功");

//                         deviceSecret = response.data.get("deviceSecret");
//                         init(pk,dn,ds);
                        return;
                    }
                } catch (Exception e) {
                }
//                ALog.d(TAG, "动态注册失败" + commonResponse.getData());
                System.out.println("动态注册失败"+ commonResponse.getData());
            }
        });
//  ####### 一型一密动态注册接口结束  ######
    }
}
