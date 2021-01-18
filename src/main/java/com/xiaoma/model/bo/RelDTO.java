package com.xiaoma.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/18 下午4:12
 */
@AllArgsConstructor
@Data
@Builder
public class RelDTO {
    private Long id;
    private List<Long> relIds;
}
