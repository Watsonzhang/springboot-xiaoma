package com.xiaoma.alg;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/18 下午3:40
 */
public class TaxMain {
    public static void main(String[] args) {
        BlockingQueue<TaxST> queue = new LinkedBlockingDeque<>(10);
        TaxProducer taxProducer = new TaxProducer(queue);
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(taxProducer);
    }
}
