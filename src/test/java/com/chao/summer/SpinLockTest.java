package com.chao.summer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockTest {

    public static void main(String[] args) throws InterruptedException {

        SpinLock spinLock = new SpinLock();

        new Thread(() -> {
            spinLock.lock();
            System.out.println("sleeping");
            try {
                TimeUnit.SECONDS.sleep(8);
                System.out.println("sleep over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                spinLock.unlock();
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            spinLock.lock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                spinLock.unlock();
            }
        }).start();


        Thread daemonThread = new Thread(() -> {
            while (true) {
                System.out.println("daemonThread activeCount: " + Thread.activeCount());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        daemonThread.setDaemon(true);
        daemonThread.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("activeCount: " + Thread.activeCount());


    }
}

class SpinLock {
    private AtomicReference<Thread> lockThread = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " locking");
        while (!lockThread.compareAndSet(null, thread)) {
        }
    }


    public void unlock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " unlocked");
        while (lockThread.compareAndSet(thread, null)) {
        }
    }
}
