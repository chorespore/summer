package com.chao.summer;

import java.util.concurrent.TimeUnit;

public class SingletonTest {

//    private static SingletonTest instance;
        private volatile static SingletonTest instance;
    private int n;

    private SingletonTest() {

    }

    @Override
    public String toString() {
        return "SingletonTest{" +
                "n=" + n +
                '}';
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public static SingletonTest getInstance() {
        if (instance == null) {
            synchronized (SingletonTest.class) {
                if (instance == null) {
                    System.out.println(Thread.currentThread().getName() + " get null");
                    SingletonTest tmp = new SingletonTest();
                    tmp.setN(6);
//                    instance = new SingletonTest();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    instance = tmp;
                }
            }
        }
        System.out.println(Thread.currentThread().getName() + " get notNull");
        return instance;
    }

    public static void main(String[] args) {
//        enumSingleton.INSTANCE.go();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " " + SingletonTest.getInstance());
            }).start();
        }
    }


}


enum enumSingleton {
    INSTANCE;

    public void go() {
        System.out.println("go");
    }
}

class Singleton {
    // 构造器私有化
    private Singleton() {
    }

    private static class SingletonHolder {
        private static Singleton instance = new Singleton();
    }


    public static Singleton getInstance() {
        return SingletonHolder.instance;
    }
}
