package com.xiaoma.zhangwei.app.annotations;

import com.xiaoma.zhangwei.app.config.HelloWorldConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/16 下午2:30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HelloWorldConfiguration.class)
public @interface EnableHelloWorld {
}
