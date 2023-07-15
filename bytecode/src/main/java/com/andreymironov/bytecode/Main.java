package com.andreymironov.bytecode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorServiceWrong = new DeadLockTriggerWrong().trigger();
        ExecutorService executorServiceRight = new DeadLockTriggerRight().trigger();

        System.out.println("Is deadlock with right: " + !executorServiceRight.awaitTermination(3, TimeUnit.SECONDS));
        System.out.println("Is deadlock with wrong: " + !executorServiceWrong.awaitTermination(3, TimeUnit.SECONDS));
        System.exit(0);
    }
}
