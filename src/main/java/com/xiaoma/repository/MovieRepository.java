package com.xiaoma.repository;


import com.xiaoma.entity.MovieEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/14 下午3:49
 */
public interface MovieRepository extends Neo4jRepository<MovieEntity, String> {
    MovieEntity findByDescription(String des);

}
