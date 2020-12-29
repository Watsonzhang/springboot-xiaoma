package com.xiaoma.zhangwei.union;

import com.xiaoma.zhangwei.xml.util.UnzipUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/23 上午10:01
 */
public class UnzipClient {
    public static void main(String[] args) throws IOException, ParseException {
        //UnzipUtil.unzip("yzf.ofd", System.getProperty("java.io.tmpdir") + File.separator+ "ofd");
        UnzipUtil.unzip("yzf.ofd", "yzf-ofd");

       /* Path workDir = Files.createTempDirectory("ofd-tmp-");
        // 解压文档，到临时的工作目录
        String s= workDir.toAbsolutePath().toString() + File.separator;
        System.out.println(s);
        UnzipUtil.unzip("yzf.ofd",s);*/
    }

    /**
     * 网络url资源转file (工具类需要个File 故需要将网络url转化成一个File)
     * @param url
     * @return
     */
    public static File resourceUrl2File(String url,String suffix) {
        InputStream ins;
        OutputStream os;
        HttpURLConnection httpUrl;
        File file = null;
        try {
            httpUrl = (HttpURLConnection) new URL(url).openConnection();
            httpUrl.connect();
            ins = httpUrl.getInputStream();
            file = new File(System.getProperty("java.io.tmpdir") + File.separator + "hello"+"."+suffix);
            os = new FileOutputStream(file);
            int bytesRead;
            int len = 8192;
            byte[] buffer = new byte[len];
            while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            httpUrl.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
