package com.xiaoma.aviator.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.xiaoma.aviator.function.type.CustomAviatorDecimal;

import java.util.Map;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/7 下午4:30
 */
public class MAXFunction extends AbstractFunction {

    @Override
    public String getName() {
        return "MAX";
    }

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject param1,AviatorObject param2) {
        long l1 = FunctionUtils.getNumberValue(param1, env).longValue();
        long l2 = FunctionUtils.getNumberValue(param2, env).longValue();
        if(l1>l2){
            return new CustomAviatorDecimal(l1);
        }
        return new CustomAviatorDecimal(l2);
    }
}
