package com.xiaoma.zhangwei.union;

import com.xiaoma.zhangwei.xml.util.UnzipUtil;

import java.io.File;
import java.io.IOException;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/23 上午10:01
 */
public class UnzipClient {
    public static void main(String[] args) throws IOException {
        UnzipUtil.unzip("yzf.ofd","yzf-test");
    }
}
