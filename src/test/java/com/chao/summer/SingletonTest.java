package com.chao.summer;

public class SingletonTest {

    private volatile static SingletonTest instance;

    private SingletonTest() {

    }

    public static SingletonTest getInstance() {
        if (instance == null) {
            synchronized (SingletonTest.class) {
                if (instance == null) {
                    instance = new SingletonTest();
                }
            }
        }
        return instance;
    }
}
