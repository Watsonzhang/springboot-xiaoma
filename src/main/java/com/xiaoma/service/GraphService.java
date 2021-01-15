package com.xiaoma.service;

import com.xiaoma.repository.MovieRepository;
import com.xiaoma.entity.PersonEntity;
import com.xiaoma.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/14 下午2:26
 */

@Service
public class GraphService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired


    public void testSave() {
        PersonEntity build = PersonEntity.builder().name("个人节点").id(1000L).build();
        personRepository.save(build);
    }
}
