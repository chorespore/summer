package com.chao.summer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinTest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        go();
        go1();
    }

    public static void go() throws ExecutionException, InterruptedException {
        Long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinSum(0L, 1_000_000_000L);

        ForkJoinTask<Long> submit = forkJoinPool.submit(task);
        System.out.println("get " + task.get());

        long res = submit.get();
        System.out.println("ForkJoinSum: " + res);

        Long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");
    }

    public static void go1() {
        Long start = System.currentTimeMillis();
        Long sum = LongStream.rangeClosed(0, 1_000_000_000L).parallel().reduce(0, Long::sum);
        System.out.println(sum);
        Long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");
    }
}


class ForkJoinSum extends RecursiveTask<Long> {
    private long first;
    private long last;

    private long block = 1000_0000L;

    public ForkJoinSum(long first, long last) {
        this.first = first;
        this.last = last;
    }

    @Override
    protected Long compute() {
        if (last - first < block) {
            long sum = 0;
            for (long i = first; i <= last; i++) {
                sum += i;
            }
            return sum;
        } else {
            long mid = (first + last) / 2;
            ForkJoinSum task1 = new ForkJoinSum(first, mid);
            ForkJoinSum task2 = new ForkJoinSum(mid + 1, last);
            task1.fork();
            task2.fork();
            return task1.join() + task2.join();
        }
    }
}
