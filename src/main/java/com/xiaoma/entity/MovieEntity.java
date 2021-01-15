package com.xiaoma.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/14 下午3:40
 */
@Node("Movie")
@Data
@Builder
public class MovieEntity {

    @Id
    private final String title;

    @Property("tagline")
    private final String description;

    @Relationship(type = "ACTED_IN", direction = Relationship.Direction.INCOMING)
    private List<Roles> actorsAndRoles;

    @Relationship(type = "DIRECTED", direction = Relationship.Direction.OUTGOING)
    private List<PersonEntity> directors = new ArrayList<>();


    // Getters omitted for brevity
}
