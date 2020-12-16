package com.xiaoma.zhangwei.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/16 下午2:28
 */
@Configuration
public class HelloWorldConfiguration {

    @Bean
    public String helloWorld(){
        return "hell,world";
    }
}
