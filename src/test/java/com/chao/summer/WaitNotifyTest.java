package com.chao.summer;

import java.util.Stack;

public class WaitNotifyTest {
    public static void main(String[] args) {
        Container container = new Container();
        new Producer(container).start();
        new Consumer(container).start();
    }
}

class Producer extends Thread {
    private Container container;

    Producer(Container container) {
        this.container = container;
    }


    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            this.container.push(i);
            System.out.println("Bean-" + i + " produced -->");
        }
    }
}

class Consumer extends Thread {
    private Container container;

    Consumer(Container container) {
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("Bean-" + this.container.pop() + " consumed <--");
        }
    }
}

class Container {
    final int N = 10;
    Stack<Integer> beans = new Stack<>();

    public synchronized void push(int bean) {
        if (beans.size() == N) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        beans.push(bean);
        this.notifyAll();
    }

    public synchronized int pop() {
        if (beans.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.notifyAll();
        return beans.pop();
    }
}
