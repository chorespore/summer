package com.chao.summer;

import org.apache.tomcat.util.http.fileupload.FileUtils;

public class DownloadTest {
    public static void main(String[] args) {

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
     * @see #stop()
     * @see #Thread(ThreadGroup, Runnable, String)
     */
    @Override
    public void run() {
    }
}
