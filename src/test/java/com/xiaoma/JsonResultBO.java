package com.xiaoma;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/19 下午1:59
 */
@Data
public class JsonResultBO {
    public JsonResultBO(Long id, Long value) {
        this.id = id;
        this.value = value;
    }

    private Long id;
    private Long value;
}
