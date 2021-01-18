package com.xiaoma.model.bo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/18 下午3:17
 */
@Data
@Builder
public class MiddleBo {
    private volatile List<RelationDTO> list;
}
