package com.cxm.iotserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * create by
 * 三和智控: cxm on 2020/3/23
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String returnIndex(){
        return "index";
    }
}
