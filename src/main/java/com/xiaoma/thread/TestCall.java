package com.xiaoma.thread;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TestCall {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        MyData myData = new MyData(100);
        List<Callable<Integer>> list = new ArrayList<>();
        list.add(new Ticket(myData));
        list.add(new Ticket(myData));
        list.add(new Ticket(myData));
        list.add(new Ticket(myData));
        list.add(new Ticket(myData));
        list.add(new Ticket(myData));
        list.add(new Ticket(myData));
        list.add(new Ticket(myData));
        list.add(new Ticket(myData));
        list.add(new Ticket(myData));
        list.add(new Ticket(myData));
        list.add(new Ticket(myData));
        List<Future<Integer>> futures = executor.invokeAll(list);
        System.out.println("main线程"+Thread.currentThread().getId());
        for(Future<Integer> stringFuture : futures) {
            System.out.println("future task: " + stringFuture.get());
        }
        executor.shutdown();
    }
}
