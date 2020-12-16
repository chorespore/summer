package com.chao.summer;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadTest {
    @Test
    public void timeUnitTest() {
        System.out.println("start");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }

    public static void main(String[] args) {
        Tick tick = new Tick();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                tick.printA();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                tick.printB();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                tick.printC();
            }
        }, "C").start();


        Phone phone = new Phone();
        new Thread(() -> phone.call(), "P-A").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> phone.message(), "P-B").start();
    }
}


class Phone {
    public static synchronized void call() {
        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Call");
    }

    public synchronized void message() {
        System.out.println("Message");
    }
}

class Tick {

    private int flag = 1;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    Lock readLock = reentrantReadWriteLock.readLock();
    Lock writeLock = reentrantReadWriteLock.writeLock();


    public void printA() {
        lock.lock();
        try {
            while (flag != 1) {
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + "=>");
            flag = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void printB() {
        lock.lock();
        try {
            while (flag != 2) {
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + "=>");
            flag = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void printC() {
        lock.lock();
        try {
            while (flag != 3) {
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "=>");
            flag = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
