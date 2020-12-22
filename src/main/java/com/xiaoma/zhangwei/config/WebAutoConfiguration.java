package com.xiaoma.zhangwei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/21 下午1:42
 */
@Configuration
public class WebAutoConfiguration {

    @Bean
    public String hello(){
        return "hello-world";
    }
}
