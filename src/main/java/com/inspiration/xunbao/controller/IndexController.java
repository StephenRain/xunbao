package com.inspiration.xunbao.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yaotianchi
 * @date 2019/9/29
 */
@Slf4j
@Controller
public class IndexController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public  String index(){
        return "/pages/index";
    }


}
