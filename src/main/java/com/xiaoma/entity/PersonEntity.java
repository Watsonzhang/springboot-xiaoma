package com.xiaoma.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/14 下午3:46
 */

@Data
@Node
@Builder
public class PersonEntity {
    @Id
    private final Long id;

    @Property("name")
    private final String name;
}
