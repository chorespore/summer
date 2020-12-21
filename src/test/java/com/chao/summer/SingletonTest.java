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

    public static void main(String[] args) {
        enumSingleton.INSTANCE.go();
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
