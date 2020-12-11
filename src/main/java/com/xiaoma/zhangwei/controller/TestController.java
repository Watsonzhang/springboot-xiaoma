package com.xiaoma.zhangwei.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/11 上午10:31
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String testHello(){
        return "hello world";
    }
}
