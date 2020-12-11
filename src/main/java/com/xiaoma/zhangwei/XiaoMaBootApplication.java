package com.xiaoma.zhangwei;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/11 上午10:30
 */
@SpringBootApplication
public class XiaoMaBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaoMaBootApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> helloWorld(){
        HandlerFunction<ServerResponse> dateFunction =
                request -> ok().body(
                        Mono.just("Today is " + new SimpleDateFormat("yyyy-MM-dd").format(new Date())), String.class);
        return route(GET("/world"), dateFunction);
    }

    @Bean
    public ApplicationRunner runner(WebServerApplicationContext context){
        return args -> {
            System.out.println("当前webserver实现类为"+context.getWebServer().getClass().getName());
        };
    }
}
