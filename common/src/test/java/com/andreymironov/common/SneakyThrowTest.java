package com.andreymironov.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SneakyThrowTest {
    private <E extends Throwable> void sneakyThrow(Throwable throwable) throws E {
        throw (E) throwable;
    }

    private void throwSneakyIOException() {
        sneakyThrow(new IOException("Oops"));
    }
    @Test
    void should_sneaky_throw() {
        Assertions.assertThatCode(this::throwSneakyIOException)
                .isInstanceOf(IOException.class);
    }
}
