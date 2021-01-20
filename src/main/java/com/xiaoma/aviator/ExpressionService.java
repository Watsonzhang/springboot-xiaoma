package com.xiaoma.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.exception.ExpressionRuntimeException;
import org.springframework.stereotype.Service;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/15 上午9:34
 */
@Service
public class ExpressionService {

    public Object execute(String exp) {
        try {

            if(exp.equals("[a]")){
                return 1L;
            }
            if(exp.equals("[b]")){
                return 2L;
            }
            if(exp.equals("[c]")){
                return 3L;
            }
            if(exp.equals("[d]")){
                return 4L;
            }
            Expression expression = AviatorEvaluator.compile(exp, true);
            Object result = expression.execute();
            return result;
        } catch (Exception e) {
            throw new ExpressionRuntimeException("执行公式" + exp + "异常", e);
        }
    }
}
