package com.xiaoma.self;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/18 下午3:26
 */
public class PCData {
    private final int intData;
    public PCData(int d){
        intData = d;
    }
    public PCData(String d){
        intData = Integer.valueOf(d);
    }
    public int getData(){
        return intData;
    }
    @Override
    public String toString(){
        return "data:"+intData;
    }
}