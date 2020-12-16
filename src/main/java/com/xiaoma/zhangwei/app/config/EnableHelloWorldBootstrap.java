package com.xiaoma.zhangwei.app.config;

import com.xiaoma.zhangwei.app.annotations.EnableHelloWorld;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/16 下午2:32
 */
@EnableHelloWorld
@Configuration
public class EnableHelloWorldBootstrap {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(EnableHelloWorldBootstrap.class);
        context.refresh();
        String helloWorld = context.getBean("helloWorld", String.class);
        System.out.println(helloWorld);


    }
}
