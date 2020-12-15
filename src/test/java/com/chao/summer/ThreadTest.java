package com.chao.summer;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {
    @Test
    public void timeUnitTest() throws InterruptedException {
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
            for (int i = 0; i < 10; i++) {
                tick.printA();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                tick.printB();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                tick.printC();
            }
        }, "C").start();
    }

    static class Tick {
        private int flag = 1;
        private ReentrantLock lock = new ReentrantLock();
        private Condition condition1 = lock.newCondition();
        private Condition condition3 = lock.newCondition();
        private Condition condition2 = lock.newCondition();


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

}
