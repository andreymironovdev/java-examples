package com.andreymironov.common;

import org.junit.jupiter.api.Test;

public class DiedReferenceTest {
    @Test
    void should_hold_died_ref() {
        DiedReference ref = new DiedReferenceImpl();

        System.gc();

        System.out.println(DiedReferenceImpl.diedReferences);
    }
}
