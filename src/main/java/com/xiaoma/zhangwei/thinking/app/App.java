package com.xiaoma.zhangwei.thinking.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/18 下午2:58
 */
@RestController
@SpringBootApplication
public class App {

    @RequestMapping("/")
    public String index(){
        return "thinking in boot";
    }
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
