package com.xiaoma.zhangwei.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/14 下午3:22
 */
@RestController
public class TestController {
    @GetMapping(value = "/hello",name = "hello")
    public String testHello(){
        return "hello controller";
    }
}
