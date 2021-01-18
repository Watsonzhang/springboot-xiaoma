package com.xiaoma.model.bo;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/18 下午1:12
 */
@Data
@Builder
public class RelationDTO {
    private Long id;
    private Long parentId;
}
