package com.andreymironov.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

public class StreamsTest {
    @Test
    void should_not_be_reused() {
        Stream<String> stringStream = Stream.of("1", "2", "3");

        List<String> list = stringStream.toList();

        Assertions.assertThatCode(() -> stringStream.findFirst().orElse(null))
                .isInstanceOf(IllegalStateException.class);
    }
}
