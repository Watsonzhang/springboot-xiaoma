package com.xiaoma.zhangwei.app.componentscan;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/16 下午5:40
 */
@ComponentScan(basePackages = "com.xiaoma.zhangwei.app.componentscan")
public class ComponentScanDefaultPackageBootStrap {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ComponentScanDefaultPackageBootStrap.class);
        String[] names = context.getBeanDefinitionNames();
        List<String> list = Arrays.asList(names);
        list.forEach(item->{
            System.out.println(item);
        });

        //MyComponent bean = context.getBean(MyComponent.class);
        //System.out.println(bean);
    }
}
