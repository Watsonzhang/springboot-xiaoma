package com.xiaoma.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/14 下午5:12
 */
@Data
@Builder
public class TaxDTO {

    private final Long id;

    private final String name;

    private List<TaxDTO> children;

    private String expression;
}
