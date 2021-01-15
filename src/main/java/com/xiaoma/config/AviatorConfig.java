package com.xiaoma.config;

import com.googlecode.aviator.AviatorEvaluator;
import com.xiaoma.aviator.function.MAXFunction;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/15 上午9:32
 */
@Component
public class AviatorConfig {

    @PostConstruct
    private void initFunctions(){
        AviatorEvaluator.addFunction( new MAXFunction());
    }
}
