package com.chao.summer;

import java.util.concurrent.TimeUnit;

public class TicketTest {
    private int ticket = 10;

    public static void main(String[] args) throws InterruptedException {
        TicketTest travaller = new TicketTest();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            travaller.buy();
        }).start();

        new Thread(() -> {
            travaller.buy();
        }).start();

        new Thread(() -> {
            travaller.buy();
        }).start();

        new Thread(() -> {
            travaller.buy();
        }).start();
    }


    public synchronized void buy() {
        while (ticket > 0) {
            try {
//                System.out.println("Selling Ticket-" + ticket + " to " + Thread.currentThread().getName());
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Ticket-" + ticket-- + " sold to " + Thread.currentThread().getName());
        }
    }
}
