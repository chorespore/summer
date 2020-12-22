package com.chao.summer;

/**
 * <li>线程礼让</li>
 * <li>礼让不一定成功</li>
 */
public class YieldJoinTest {
    public static void main(String[] args) throws InterruptedException {
        MyYield myYield = new MyYield();
        new Thread(myYield, "A").start();
        new Thread(myYield, "B").start();

        Thread vipThread = new Thread(new MyJoin(), "VIP");
        vipThread.start();
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                vipThread.join(1);
            }
            System.out.println("main-" + i);
        }
    }
}

class MyYield implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start");
        Thread.yield();
        System.out.println(Thread.currentThread().getName() + " end");
    }
}


class MyJoin implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("VIP-" + i);
        }
    }
}
