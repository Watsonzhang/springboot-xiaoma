package com.xiaoma.zhangwei.app.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.AnnotatedElement;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/15 下午4:19
 */
@Service(value = "hello")
public class ReflectService {
    public static void main(String[] args) {
        AnnotatedElement element= TransactionalServiceBootstrap.class;
        Service annotation = element.getAnnotation(Service.class);
        ReflectionUtils.doWithMethods(Service.class,method -> System.out.printf("@Service.%s()=%s\n",method.getName(),ReflectionUtils.invokeMethod(method,annotation))
                ,method -> method.getParameterCount()==0);

    }

    @Override
    public String toString() {
        System.out.println("看看");
        return "ReflectService{}";
    }
}
