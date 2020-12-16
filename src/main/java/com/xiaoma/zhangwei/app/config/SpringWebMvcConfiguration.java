package com.xiaoma.zhangwei.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/16 下午4:07
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = SpringWebMvcConfiguration.class)
public class SpringWebMvcConfiguration {

}
