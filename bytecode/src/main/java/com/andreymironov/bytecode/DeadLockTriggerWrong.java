package com.andreymironov.bytecode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;

public class DeadLockTriggerWrong {
    public ExecutorService trigger() {
        CountDownLatch latch = new CountDownLatch(1);
        BiFunction<Object, Object, Runnable> runnableBiFunction = (lock1, lock2) -> () -> {
            try {
                synchronized (lock1) {
                    latch.await();
                    synchronized (lock2) {
                        System.out.println("Do some work");
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        };

        Object lock1 = new Object();
        Object lock2 = new Object();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(runnableBiFunction.apply(lock1, lock2));
        executorService.submit(runnableBiFunction.apply(lock2, lock1));

        latch.countDown();
        executorService.shutdown();
        return executorService;
    }
}
