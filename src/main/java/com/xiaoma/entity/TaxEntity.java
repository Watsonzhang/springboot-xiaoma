package com.xiaoma.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/14 下午5:09
 */
@Node
@Builder
@AllArgsConstructor
@Data
public class TaxEntity {
    @Id
    private final Long id;

    @Property("name")
    private final String name;

    @Relationship(type = "DIRECTED", direction = Relationship.Direction.OUTGOING)
    private List<TaxEntity> children;

    @Property("expression")
    private String expression;

    private Integer calValue;
}
