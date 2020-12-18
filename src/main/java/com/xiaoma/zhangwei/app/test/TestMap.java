package com.xiaoma.zhangwei.app.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/18 上午10:58
 */
public class TestMap {
    public static void main(String[] args) {
        Map<String,String> stringHashMap = new HashMap<>();
        stringHashMap.put("a","A");
        stringHashMap.put("b","B");
        stringHashMap.forEach((key,value)->{
            System.out.println(key);
            System.out.println(value);
        });
    }
}
