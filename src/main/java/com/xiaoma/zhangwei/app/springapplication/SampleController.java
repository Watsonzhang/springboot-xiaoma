package com.xiaoma.zhangwei.app.springapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/17 上午9:29
 */
@Controller
@EnableAutoConfiguration
public class SampleController {
    @GetMapping("/home")
    @ResponseBody
    public String home(){
        return "this is sample";
    }
    public static void main(String[] args) {
        SpringApplication.run(SampleController.class,args);
    }
}
