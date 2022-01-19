package com.chao.summer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicTest {
    private static volatile int num = 0;
    private static AtomicInteger atomicNum = new AtomicInteger();


    public static void main(String[] args) throws InterruptedException {
        System.out.println("Thread.activeCount: " + Thread.activeCount());

//        sum();
//        atomicSum();
        // cas();
//        pause();
        prime()
    }

    public void prime() {
        AtomicBoolean ok = new AtomicBoolean(false);
        Scanner in = new Scanner(System.in);
        System.out.print("Seach for primes whthin:");
        int n = in.nextInt();
        System.out.println("Seaching for primes whthin " + n + "...");
        new Thread(() -> {
            long start = System.currentTimeMillis();
            while (ok.get() == false) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}
                long end = System.currentTimeMillis();
                System.out.print("Time Used:" + (end - start) + "ms\r");
            }
        }).start();
        find(n);
        ok.set(true);
        in.close();
    }


    public  void find(int n) {
        long start = System.currentTimeMillis(); // 取开始时间
        int j, num = 0;
        boolean sgin;
        for (int i = 2; i <= n; i++) {
            if (i % 2 == 0 && i != 2)
                continue; // 偶数和1排除
            sgin = true;
            for (j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    sgin = false;
                    break;
                }
            }
            if (sgin) { // 打印
                num++;
            }
        }
        System.out.println(n + "以内的素数有" + num + "个");
        long end = System.currentTimeMillis();
        System.out.println("Time Used:" + (end - start) + "ms\n");
    }

    public static void cas() {
        AtomicInteger n = new AtomicInteger(6);
        n.compareAndSet(16, 18);
        System.out.println(n);
        n.compareAndSet(6, 8);
        System.out.println(n);


        // 对于 Integer var = ? 在-128 至 127 之间的赋值， Integer 对象是在 IntegerCache.cache 产生，
        //会复用已有对象，这个区间内的 Integer 值可以直接使用==进行判断，但是这个区间之外的所有数据，都
        //会在堆上产生，并不会复用已有对象
        AtomicStampedReference<Integer> stampedNum = new AtomicStampedReference<>(106, 1);
        int stamp = stampedNum.getStamp();
        System.out.println(stampedNum.compareAndSet(106, 18, stamp, stamp + 1));
        System.out.println(stampedNum.getReference());
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
