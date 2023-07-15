package com.andreymironov.concurrency.doublecheckedlocking;

import java.util.Random;

public class EscapingReferenceResource {
    private final int value;
    private final String name;

    public EscapingReferenceResource(EscapingReferenceDCLResourceFactory factory, int value, String name) {
        // escape reference
        factory.setResource(this);

        // perform long timed operation
        for (int i = 0; i < 1000000; i++) {
            if (new Random().nextInt() == System.currentTimeMillis()) {
                System.out.print(" ");
            }
        }

        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
