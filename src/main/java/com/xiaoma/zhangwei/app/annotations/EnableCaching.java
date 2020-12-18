package com.xiaoma.zhangwei.app.annotations;

import com.xiaoma.zhangwei.app.selector.CachingConfigurationSelector;
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
@Import(CachingConfigurationSelector.class)
public @interface EnableCaching {
}
