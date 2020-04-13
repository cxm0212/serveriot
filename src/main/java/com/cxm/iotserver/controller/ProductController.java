package com.cxm.iotserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.iot.model.v20180120.QueryProductListResponse;
import com.cxm.iot.api.sdk.openapi.ProductManager;
import com.cxm.iotserver.util.PrintWriterUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create by
 * 三和智控: cxm on 2020/3/23
 */
@Controller
public class ProductController {

    @RequestMapping(value = "/findproductlist")
    public void findProductList(HttpServletRequest res, HttpServletResponse resp){
        QueryProductListResponse.Data data = ProductManager.queryProductListTest(1,20,null);


        JSONArray array = JSONArray.parseArray(JSON.toJSONString(data.getList()));
        System.out.println(array);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rows",data.getList());

        PrintWriterUtil.writerJSON(resp,jsonObject,null);

    }
}
