package com.chao.summer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("runAsync");
        });


        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "OK";
        });

        completableFuture.whenComplete((t, u) -> {
            System.out.println("T: " + t);
            System.out.println("U: " + u);
        }).exceptionally((e) -> {
            System.out.println(e.getCause() + "|" + e.getMessage());
            return "FAILED";
        });

        String res = completableFuture.get();
        System.out.println(res);
        runAsync.get();
    }
}
