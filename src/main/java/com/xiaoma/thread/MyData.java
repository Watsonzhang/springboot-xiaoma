package com.xiaoma.thread;

import lombok.Data;


public class MyData {
    private volatile Integer number;

    public MyData(Integer number) {
        this.number = number;
    }

    public synchronized Integer getNumber() {
        return number;
    }

    public synchronized void setNumber(Integer number) {
        this.number = number;
    }
}
