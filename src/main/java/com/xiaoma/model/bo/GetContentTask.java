package com.xiaoma.model.bo;

import java.util.concurrent.Callable;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/18 下午2:43
 */
public class GetContentTask implements Callable<String> {

    private String name;

    private Integer sleepTimes;

    public GetContentTask(String name, Integer sleepTimes) {
        this.name = name;
        this.sleepTimes = sleepTimes;
    }
    public String call() throws Exception {
        // 假设这是一个比较耗时的操作
        Thread.sleep(sleepTimes * 1000);
        return "this is content : hello " + this.name;
    }

}
