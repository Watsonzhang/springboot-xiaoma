package com.xiaoma.thread;

import lombok.Data;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class Ticket implements Callable<Integer> {
    private AtomicInteger integer;

    public Ticket(AtomicInteger integer) {
        this.integer=integer;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(100);
        return integer.getAndDecrement();
    }
}
