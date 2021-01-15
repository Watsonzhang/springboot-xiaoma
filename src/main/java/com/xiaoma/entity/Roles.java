package com.xiaoma.entity;

import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.List;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/14 下午3:46
 */
@RelationshipProperties
public class Roles {

    private final List<String> roles;

    @TargetNode
    private final PersonEntity person;

    public Roles(PersonEntity person, List<String> roles) {
        this.person = person;
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }
}
