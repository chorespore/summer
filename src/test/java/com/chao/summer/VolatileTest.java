package com.chao.summer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest {
    private static volatile int num = 0;
    private static AtomicInteger atomicNum = new AtomicInteger();


    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.activeCount());

        sum();
        atomicSum();
//        pause();

    }

    public static void atomicSum() {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicNum.getAndIncrement();
                }
            }).start();
        }

        while (Thread.activeCount() > 1) {
            System.out.println("yield");
            Thread.yield();
        }
        System.out.println("atomicNum: " + atomicNum);
    }

    public static void sum() {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount() > 2) {
            System.out.println("yield");
            Thread.yield();
        }
        System.out.println("num: " + num);
    }

    public static synchronized void add() {
        num++;
    }

    public static void pause() throws InterruptedException {
        new Thread(() -> {
            while (num == 0) {

            }
        }).start();

        TimeUnit.SECONDS.sleep(2);

        num = 1;
        System.out.println(num);
    }
}
