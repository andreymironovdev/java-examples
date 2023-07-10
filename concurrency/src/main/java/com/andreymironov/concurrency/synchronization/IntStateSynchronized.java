package com.andreymironov.concurrency.synchronization;

public class IntStateSynchronized implements IntState {
    private int current;

    @Override
    public synchronized int getNext() {
        return ++current;
    }

    @Override
    public int getCurrent() {
        return current;
    }
}
