package com.xiaoma.repository;

import com.xiaoma.entity.TaxEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/14 下午5:28
 */
public interface TaxEntityRepository extends Neo4jRepository<TaxEntity, Long> {
    TaxEntity findByExpression(String item);
    TaxEntity findByName(String name);
}
