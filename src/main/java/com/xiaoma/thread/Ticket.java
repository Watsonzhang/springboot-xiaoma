package com.xiaoma.thread;

import lombok.Data;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


@Data
public class Ticket implements Callable<Integer> {
    private MyData data;

    public Ticket(MyData data) {
        this.data = data;
    }

    @Override
    public Integer call() throws Exception {
        synchronized (Ticket.class){
            Thread.sleep(200);
            MyData data = getData();
            data.setNumber(data.getNumber()-1);
            return data.getNumber();
        }
    }
}
