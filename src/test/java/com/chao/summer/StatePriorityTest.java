package com.chao.summer;

import java.util.concurrent.TimeUnit;

public class StatePriorityTest {


    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName() + " Priority " + thread.getPriority() + " State " + thread.getState());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);
        t3.setPriority(8);

        System.out.println(" State " + t1.getState());

        t1.start();
        t2.start();
        t3.start();

        while (t1.getState() != Thread.State.TERMINATED) {
            System.out.println(" State " + t1.getState());
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }
}


