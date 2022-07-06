package com.chao.summer;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;


public class ImageTest {

    @Test
    public void cutImage() {
        ImgUtil.cut(
                FileUtil.file("C:\\Users\\Chao\\Desktop\\ocr\\id1.PNG"),
                FileUtil.file("C:\\Users\\Chao\\Desktop\\ocr\\id1CUT.PNG"),
                new Rectangle(200, 200, 100, 100)//裁剪的矩形区域
        );
    }

    @Test
    public void imgToBase64Test() throws IOException {
//        final byte[] imgBytes = getBytesByURL("https://static.www.tencent.com/uploads/2022/06/02/81715eb2fb84b6b8d5be3c1f9c154a42.png");
        final byte[] imgBytes = Files.readAllBytes(new File("C:\\Users\\Chao\\Desktop\\ocr\\hk2.jpg").toPath());
        String base64 = Base64.getEncoder().encodeToString(imgBytes);
        System.out.println(base64);
    }

    private byte[] getBytesByURL(String imageUrl) throws IOException {
        System.out.println("imageUrl:" + imageUrl);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedInputStream bis = null;
        HttpURLConnection urlconnection = null;
        URL url = null;
        byte[] buf = new byte[1024];
        try {
            url = new URL(imageUrl);
            urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.connect();
            bis = new BufferedInputStream(urlconnection.getInputStream());
            for (int len = 0; (len = bis.read(buf)) != -1; ) {
                baos.write(buf, 0, len);
            }
            return baos.toByteArray();
        } finally {
            try {
                urlconnection.disconnect();
                bis.close();
            } catch (IOException ignore) {
                System.out.println("下载错误:");
            }
        }
    }

}
