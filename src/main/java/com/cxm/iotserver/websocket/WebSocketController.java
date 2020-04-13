package com.cxm.iotserver.websocket;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * create by
 * 三和智控: cxm on 2020/4/2
 */
@Controller
public class WebSocketController {

    @Autowired
    private WebSocketServer webSocketServer;


    /**
     * 发送消息页面
     *
     * @param mesaage
     * @return
     */
    @RequestMapping("/webSocket/senMessage")
    public ModelAndView senMessage(String mesaage) {
        ModelAndView mav = new ModelAndView("/sendMessage");
        return mav;
    }

    /**
     * 接收消息页面
     *
     * @param mesaage
     * @return
     */
    @RequestMapping("/webSocket/receiveMessage")
    public ModelAndView receiveMessage(String mesaage) {
        ModelAndView mav = new ModelAndView("/receiveMessage");

        return mav;
    }

    /**
     * 服务器端推送消息
     *
     * @return
     */
    @RequestMapping("/serverSendMessage")
    @ResponseBody
    public JSONObject sendMessage(String message) {
        JSONObject result = new JSONObject();
        try {
            result.put ("code","1000");
            result.put("msg","发送消息成功");
//            webSocketServer.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            result.put ("code","1001");
            result.put("msg","发送消息失败");
        }
        return result;
    }

}
