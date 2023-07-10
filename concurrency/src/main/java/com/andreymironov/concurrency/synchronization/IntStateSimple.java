package com.andreymironov.concurrency.synchronization;

public class IntStateSimple implements IntState {
    private int current;

    @Override
    public int getNext() {
        return ++current;
    }

    @Override
    public int getCurrent() {
        return current;
    }
}
