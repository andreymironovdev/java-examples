package com.andreymironov.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CovariantTypesTest {
    @Test
    void should_arrays_be_covariant() {
        Object[] objects = new Long[1];
        Assertions.assertThatCode(() -> objects[0] = "Hello").isInstanceOf(ArrayStoreException.class);
    }

    @Test
    void should_lists_be_invariant() {
//        won't compile
//        List<Object> objects = new ArrayList<Long>();
    }
}
