package com.cxm.iot.api.sdk.openapi;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.PubRequest;
import com.aliyuncs.iot.model.v20180120.PubResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create by
 * 三和智控: cxm on 2020/3/19
 */
@WebServlet(value = "/mainservlet")
public class ServerTestMain extends HttpServlet {

    static String productKey = "a1zMD8aob6U";

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get");


        test();
        resp.getWriter().write("success");


    }



    public static void test(){
        String accessKey = "LTAI4FpZ2mhMXSLtLLLMLJJB";
        String accessSecret = "RpSfK7n1luxlWdnsmky24SX8srnKSv";
        try {
            DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Iot", "iot.cn-shanghai.aliyuncs.com");

            IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKey, accessSecret);
            DefaultAcsClient client = new DefaultAcsClient(profile); //初始化SDK客户端

            PubRequest request = new PubRequest();
            request.setProductKey("a1zMD8aob6U");
            request.setMessageContent(Base64.encodeBase64String("hello world".getBytes()));
            request.setTopicFullName("/a1zMD8aob6U/Jkwof7TK0M6sKIrcNrlE/user/get");
            request.setQos(0); //目前支持QoS0和QoS1

            PubResponse response = null;
            response = client.getAcsResponse(request);
            System.out.println(response.getSuccess());
            System.out.println(response.getErrorMessage());

        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test();
//        DeviceManager.queryDevice(productKey,null,null);
    }
}
