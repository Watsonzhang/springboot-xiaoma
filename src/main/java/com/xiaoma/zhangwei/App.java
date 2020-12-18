package com.xiaoma.zhangwei;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

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


    @EventListener(WebServerInitializedEvent.class)
    public void onWebServerReady(WebServerInitializedEvent event){
        System.out.println("当前webserver实现类为："+event.getWebServer().getClass().getName());
    }

    //@Bean
    public ApplicationRunner runner(WebServerApplicationContext context){
        return args -> {
            System.out.println("当前webserver实现类为："+context.getWebServer().getClass().getName());
        };
    }

    @Bean
    public RouterFunction<ServerResponse> helloWorld(){
        return route(GET("/hello-world"),request->ok().body(Mono.just("hello world"),String.class));
    }
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
