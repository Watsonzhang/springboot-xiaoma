package com.xiaoma.zhangwei.xml;

import com.xiaoma.zhangwei.xml.util.OfdXmlUtil;
import com.xiaoma.zhangwei.xml.util.UnzipUtil;

import java.io.IOException;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/22 上午9:52
 */
public class TestClient {
    public static void main(String[] args) throws IOException {
      /*  String unzipPath="/home/yzf/tax/ofdFile/yzf-ofd";
        String ofdFilePath="/home/yzf/tax/ofdFile/789168922258706432.ofd";*/
        String unzipPath="/home/yzf/tax/ofdFile/ruizhen-ofd";
        String ofdFilePath="/home/yzf/tax/ofdFile/ruizhen.ofd";
        UnzipUtil.unzip(ofdFilePath,unzipPath);

    }
}
