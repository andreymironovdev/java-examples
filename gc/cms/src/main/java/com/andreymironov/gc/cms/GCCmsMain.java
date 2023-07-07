package com.andreymironov.gc.cms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GCCmsMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("CMS gc running...");

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
