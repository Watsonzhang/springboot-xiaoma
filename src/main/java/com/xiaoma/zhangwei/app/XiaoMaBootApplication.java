package com.xiaoma.zhangwei.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/11 上午10:30
 */
@SpringBootApplication(scanBasePackages = "com.xiaoma.zhangwei.config")
public class XiaoMaBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaoMaBootApplication.class, args);
    }

}
