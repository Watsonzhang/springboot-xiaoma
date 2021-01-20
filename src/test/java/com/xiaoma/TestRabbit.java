package com.xiaoma;

import com.xiaoma.rabbit.producer.Producer;
import com.xiaoma.service.GraphService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/20 下午6:50
 */

@SpringBootTest(classes = {App.class})
@RunWith(SpringRunner.class)
public class TestRabbit {

    @Autowired
    Producer producer;

    @Test
    public void testSend(){
        producer.sendDirectMessage();
    }
}
