package com.chao.summer;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        tickTest();
//        phoneTest();
//        semaphoreTest();
//        readWriteLockTest();
//        blockingQueueTest();
//        unsafeListTest();
//        callableTest();
        poolTest();

    }

    public static void callableTest() throws ExecutionException, InterruptedException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(6);
        Callable callable = (Callable<String>) () -> "Solution1:" + UUID.randomUUID().toString();

        FutureTask<String> futureTask1 = new FutureTask<>(callable);
        FutureTask<String> futureTask2 = new FutureTask<>(() -> "Solution2:" + UUID.randomUUID().toString());
        FutureTask<String> futureTask3 = new FutureTask<>(() -> "Solution3:" + UUID.randomUUID().toString());

        new Thread(futureTask1).start();
        fixedThreadPool.execute(futureTask2);
        Future<String> submit = fixedThreadPool.submit(futureTask3, "ss");

        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
        System.out.println(futureTask3.get());
        // submit() 的返回值 Future 调用get方法时，可以捕获处理异常
        System.out.println(submit.get());

        fixedThreadPool.shutdown();
    }

    public static void unsafeListTest() {
        List<String> list = new ArrayList<>();
        List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        List<String> unsafeList = new ArrayList<>();
        List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                unsafeList.add(Thread.currentThread().getName());
                synchronizedList.add(Thread.currentThread().getName());
                copyOnWriteArrayList.add(Thread.currentThread().getName());
                synchronized (list) {
                    list.add(Thread.currentThread().getName());
                }

            }).start();
        }
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println("copyOnWriteArrayList:" + copyOnWriteArrayList.size());
        System.out.println("synchronized block ArrayList:" + list.size());
        System.out.println("synchronizedList:" + synchronizedList.size());
        System.out.println("ArrayList:" + unsafeList.size());
    }

    public static void blockingQueueTest() {//并发限流
        BlockingQueue queue = new ArrayBlockingQueue<Integer>(2);

        for (int i = 0; i < 3; i++) {
            System.out.println(queue.offer(i));
        }
        for (int i = 0; i < 3; i++) {
            System.out.println(queue.poll());
        }

        System.out.println("Timeout => ");

        for (int i = 0; i < 3; i++) {
            try {
                System.out.println(queue.offer(i, 1, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 3; i++) {
            try {
                System.out.println(queue.poll(1, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //FIXME
        for (int i = 0; i < 3; i++) {
            try {
                queue.add(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 3; i++) {
            try {
                queue.remove();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 3; i++) {
            try {
                queue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 3; i++) {
            try {
                queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    /**
     * 3大方法，7大参数，4种拒绝策略
     */
    public static void poolTest() {
        // 允许的请求队列workQueue长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

        // 允许的请求队列workQueue长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(6);

        // 允许的创建线程数量maximumPoolSize为 Integer.MAX_VALUE， 可能会创建大量的线程，从而导致 OOM。
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(8);

        /**
         * scheduleAtFixedRate
         * period：连续执行任务之间的周期，从上一个任务开始执行时计算延迟多少开始执行下一个任务，但是还会等上一个任务结束之后。
         */

        /**
         * scheduleWithFixedDelay
         * period：连续执行任务之间的周期，从上一个任务全部执行完成时计算延迟多少开始执行下一个任务
         */

        scheduledExecutorService.scheduleAtFixedRate(() -> System.out.println(Thread.currentThread().getName()), 1, 1, TimeUnit.SECONDS);
        ScheduledFuture<String> scheduledFuture2 = scheduledExecutorService.schedule(() -> "call-2", 2, TimeUnit.SECONDS);

        try {
            System.out.println(scheduledFuture2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } /*finally {

        }*/

        ThreadFactory summerThreadFactory = new CustomizableThreadFactory("summerThread-pool-");

        ExecutorService myThreadPool = new ThreadPoolExecutor(2, 5,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(3),
                summerThreadFactory, new ThreadPoolExecutor.AbortPolicy());
//        new ThreadPoolExecutor.AbortPolicy()
//        new ThreadPoolExecutor.CallerRunsPolicy()
//        new ThreadPoolExecutor.DiscardPolicy()
//        new ThreadPoolExecutor.DiscardOldestPolicy()
        try {
            for (int i = 0; i < 9; i++) {
                myThreadPool.submit(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } finally {
            myThreadPool.shutdown();
//            scheduledExecutorService.shutdown();
        }
    }

    @Test
    public void useSynchronousQueue() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        SynchronousQueue<Object> synchronousQueue = new SynchronousQueue<>();

        Runnable producer = () -> {
            String object = "signal";
            try {
                System.out.println("produced {}" + object);
                synchronousQueue.put(object);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable consumer = () -> {
            try {
                Object object = synchronousQueue.take();
                System.out.println("consumed {}" + object);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        executor.submit(producer);
        executor.submit(consumer);

        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();
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

    public static void readWriteLockTest() {
        CacheLock cache = new CacheLock();
        for (int i = 0; i < 6; i++) {
            String finalI = "T" + i;
            new Thread(() -> {
                cache.put("key", finalI);
            }).start();
        }

        for (int i = 0; i < 6; i++) {
            String finalI = "key";
            new Thread(() -> {
                cache.get(finalI);
//                System.out.println(cache.get(finalI));
            }).start();
        }
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
}

class CacheLock {
    private volatile Map<String, Object> map = new HashMap<>();
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);
    final Lock readLock = reentrantReadWriteLock.readLock();
    final Lock writeLock = reentrantReadWriteLock.writeLock();

    public void put(String key, Object value) {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " Write... " + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + " Write Complete");
        } finally {
            writeLock.unlock();
        }
    }

    public Object get(String key) {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " Read... " + key);
            Object obj = map.get(key);
            System.out.println(obj);
            System.out.println(Thread.currentThread().getName() + " Read Complete");
            return obj;
        } finally {
            readLock.unlock();
        }
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
