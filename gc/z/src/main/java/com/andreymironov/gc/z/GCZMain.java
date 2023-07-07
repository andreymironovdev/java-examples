package com.andreymironov.gc.z;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GCZMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Z gc running...");

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        while (true) {
            executorService.submit(() -> {
                for (int i = 0; i < 1_000_000; i++) {
                    Object object = new Object();
                    object.hashCode();
                }
            });

            Thread.sleep(10000);
        }

    }
}
