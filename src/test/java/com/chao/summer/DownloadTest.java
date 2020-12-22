package com.chao.summer;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DownloadTest {
    public static void main(String[] args) {
        new Downloader("https://dss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/topnav/baike@2x-1fe3db7fa6.png", "a.png").start();
        new Downloader("https://dss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/topnav/tupian@2x-482fc011fc.png", "b.png").start();
    }
}

class Downloader extends Thread {
    private String url;
    private String fileName;

    public Downloader(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    /**
     * If this thread was constructed using a separate
     * {@code Runnable} run object, then that
     * {@code Runnable} object's {@code run} method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of {@code Thread} should override this method.
     *
     * @see #start()
     */
    @Override
    public void run() {
        System.out.println(fileName + " Downloading...");
        try {
            FileUtils.copyURLToFile(new URL(url), new File(fileName), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileName + " OK");
    }
}
