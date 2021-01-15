package com.xiaoma.repository;

import com.xiaoma.entity.PersonEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/14 下午3:52
 */
public interface PersonRepository extends Neo4jRepository<PersonEntity, Long> {
    PersonEntity findByName(String name);
}
