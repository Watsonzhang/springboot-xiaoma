package com.xiaoma.zhangwei.app.slelector;

import com.xiaoma.zhangwei.app.annotations.EnableCaching;
import org.springframework.cache.annotation.ProxyCachingConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.AutoProxyRegistrar;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/16 下午2:37
 */
public class CachingConfigurationSelector extends AdviceModeImportSelector<EnableCaching> {
    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode){
            case PROXY:
                return new String[]{AutoProxyRegistrar.class.getName(), ProxyCachingConfiguration.class.getName()};
            default:
                return null;
        }
    }
}
