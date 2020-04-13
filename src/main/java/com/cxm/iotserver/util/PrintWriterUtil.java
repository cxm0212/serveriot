package com.cxm.iotserver.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * create by
 * 三和智控: cxm on 2019/8/27
 */
public class PrintWriterUtil {

    public static void writerText(HttpServletResponse response, String str) {
        try {
            response.getWriter().print(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writerJSON(HttpServletResponse response, Object object,  String dateFormat) {
        try {
            PrintWriter writer = response.getWriter();
            JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            if (null != dateFormat && dateFormat != "") {
                JSON.DEFFAULT_DATE_FORMAT = dateFormat;
            }
            String str = JSON.toJSONString(object, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteNullStringAsEmpty,
                    SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.DisableCircularReferenceDetect);
            writer.print(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String objToJsonStr(Object object,String dateFormat){
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm ss";
        if (null != dateFormat && dateFormat != "") {
            JSON.DEFFAULT_DATE_FORMAT = dateFormat;
        }
        String str = JSON.toJSONString(object, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect);
        return str;
    }


}
