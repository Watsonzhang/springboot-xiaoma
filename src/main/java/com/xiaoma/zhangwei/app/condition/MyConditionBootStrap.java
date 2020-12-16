package com.xiaoma.zhangwei.app.condition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/16 下午5:16
 */
@Configuration
public class MyConditionBootStrap {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MyConditionBootStrap.class);
        context.refresh();
        String helloWorld = context.getBean("helloWorld", String.class);
        String helloJava = context.getBean("helloJava", String.class);
        System.out.println(helloWorld);
        System.out.println(helloJava);
    }

    @Bean
    @Conditional(value = MyCondition.class)
    public String helloWorld() {
        return "hell,world";
    }


    @Bean
    @ConditionalOnBean(name = "helloWorld")
    public String helloJava() {
        return "hell,java";
    }
}
