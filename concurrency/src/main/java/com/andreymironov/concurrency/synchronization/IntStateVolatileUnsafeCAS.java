package com.andreymironov.concurrency.synchronization;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class IntStateVolatileUnsafeCAS implements IntState {
    private final Unsafe unsafe;
    private volatile int current;
    private final long offset;

    public IntStateVolatileUnsafeCAS() throws NoSuchFieldException, IllegalAccessException {
        this.unsafe = getUnsafe();
        this.offset = unsafe.objectFieldOffset(IntStateVolatileUnsafeCAS.class.getDeclaredField("current"));
    }

    private Unsafe getUnsafe() throws IllegalAccessException, NoSuchFieldException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }


    @Override
    public int getNext() {
        int previous = current;
        while (!unsafe.compareAndSwapInt(this, offset, previous, previous + 1)) {
            previous = current;
        }
        return current;
    }

    @Override
    public int getCurrent() {
        return current;
    }
}
