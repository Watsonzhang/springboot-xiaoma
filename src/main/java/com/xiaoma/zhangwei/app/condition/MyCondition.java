package com.xiaoma.zhangwei.app.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/16 下午5:15
 */
public class MyCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        Environment environment = conditionContext.getEnvironment();
        System.out.println(conditionContext);
        System.out.println(annotatedTypeMetadata);
        return true;
    }
}
