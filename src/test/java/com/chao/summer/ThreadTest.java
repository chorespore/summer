package com.chao.summer;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;
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


    @Test
    public void countUpTest() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> System.out.println("召唤神龙！"));
        for (int i = 0; i < 7; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " get ball" + finalI);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "T" + i).start();
        }
    }

    @Test
    public void countDownTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " left");
                countDownLatch.countDown();
            }, "Student-" + i).start();
        }

        countDownLatch.await();
        System.out.println("Door closed");
    }

    public static void semaphoreTest() {//并发限流
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " in");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + " left");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }

    @Test
    public void listTest() {
        List<String> list = new CopyOnWriteArrayList<>();
        List<String> list1 = new Vector<>();
        List<String> list2 = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().replace("-", ""));
                System.out.println(list);
            }, "T" + i).start();
        }
    }

    public static void main(String[] args) {
        tickTest();
        phoneTest();
        semaphoreTest();
    }


    public static void phoneTest() {
        Phone phone = new Phone();
        new Thread(() -> phone.call(), "P-A").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> phone.message(), "P-B").start();

    }

    public static void tickTest() {
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
    }
}


class Phone {
    public static synchronized void call() {
        try {
            TimeUnit.SECONDS.sleep(4);
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
