package com.andreymironov.concurrency.synchronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IntStateExplicitLock implements IntState {
    private int current;
    private final Lock lock = new ReentrantLock();

    @Override
    public int getNext() {
        try {
            lock.lock();
            current++;
            return current;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getCurrent() {
        return current;
    }
}
