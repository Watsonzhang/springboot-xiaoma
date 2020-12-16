package com.xiaoma.zhangwei.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/16 下午4:02
 */
@Controller
public class HelloWorldController {

    @RequestMapping(value = "hello-world",method= RequestMethod.GET)
    @ResponseBody
    public String helloWorld(){
        return "this is hello-world controller";
    }

}
