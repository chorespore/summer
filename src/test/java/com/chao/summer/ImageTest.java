package com.chao.summer;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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

    @Test
    public void urlFileTest() {
        String url = "https://static.www.tencent.com/uploads/2022/06/02/81715eb2fb84b6b8d5be3c1f9c154a42.png";
        boolean valid = urlValid(url);
        byte[] bytes = getBytesByURL(url);
        System.out.println(valid);
        System.out.println(bytes != null ? bytes.length : 0);
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        String a = fileName.substring(0, fileName.lastIndexOf('.'));
        System.out.println(fileName);
        System.out.println(a);
    }

    private boolean urlValid(String url) {
        try {
            URLConnection urlCon = new URL(url).openConnection();
            //文件存在: ‘HTTP/1.1 200 OK’ 文件不存在: ‘HTTP/1.1 404 Not Found’
            String message = urlCon.getHeaderField(0);
            if (message.contains("OK")) {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }


    private byte[] getBytesByURL(String imageUrl) {
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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
