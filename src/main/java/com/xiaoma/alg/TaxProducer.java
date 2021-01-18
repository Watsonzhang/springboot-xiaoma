package com.xiaoma.alg;

import lombok.AllArgsConstructor;

import java.util.concurrent.BlockingQueue;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/18 下午3:39
 */
@AllArgsConstructor
public class TaxProducer implements Runnable {
    private BlockingQueue<TaxST> queue;

    @Override
    public void run() {

    }
}
