package com.andreymironov.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class LambdaTest {
    @Test
    void should_use_final() {
        final int i = 10;

        Function<String, Integer> lambda = str -> str.length() + i;

        Assertions.assertThat(lambda.apply("")).isEqualTo(10);
    }

    @Test
    void should_use_effectively_final() {
        int i = 10;

        Function<String, Integer> lambda = str -> str.length() + i;

        Assertions.assertThat(lambda.apply("")).isEqualTo(10);
    }
}
