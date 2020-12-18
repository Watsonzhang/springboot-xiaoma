package com.xiaoma.zhangwei.app.event;

import org.springframework.boot.autoconfigure.AutoConfigurationImportEvent;
import org.springframework.boot.autoconfigure.AutoConfigurationImportListener;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;
import java.util.Set;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/18 上午9:34
 */
public class DefaultAutoConfigurationImportListener implements AutoConfigurationImportListener {
    @Override
    public void onAutoConfigurationImportEvent(AutoConfigurationImportEvent event) {
        ClassLoader classLoader = event.getClass().getClassLoader();
        List<String> candidates = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, classLoader);
        List<String> configurations = event.getCandidateConfigurations();
        Set<String> exclusions = event.getExclusions();

    }
}
