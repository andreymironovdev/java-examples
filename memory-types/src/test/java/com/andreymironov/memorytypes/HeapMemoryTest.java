package com.andreymironov.memorytypes;

import org.junit.jupiter.api.Test;

public class HeapMemoryTest extends BaseTest {

    @Test
    public void should_cause_oom() {
        softAssertions.assertThatCode(() -> {
            long[] array = new long[1_000_000_000];
        }).isInstanceOf(OutOfMemoryError.class);
    }

}