package com.chao.summer;

public class StaticProxyTest {

    public static void main(String[] args) {
        new WeddingCompany(new You("Alice")).marry();
    }
}

@FunctionalInterface
interface Marry {
    public abstract void marry();

    public default void amarry() {

    }
}

class You implements Marry {

    private String bride;

    public You(String bride) {
        this.bride = bride;
    }

    @Override
    public void marry() {
        System.out.println("Happy marry to " + bride);
    }
}

class WeddingCompany implements Marry {
    private Marry target;

    WeddingCompany(Marry target) {
        this.target = target;
    }

    @Override
    public void marry() {
        before();
        target.marry();
        after();
    }

    private void before() {
        System.out.println("Prepare for Wedding");
    }

    private void after() {
        System.out.println("Wedding OK");
    }
}
