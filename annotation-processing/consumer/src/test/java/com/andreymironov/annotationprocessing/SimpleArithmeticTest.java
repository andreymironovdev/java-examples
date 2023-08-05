package com.andreymironov.annotationprocessing;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleArithmeticTest {
    @Test
    void should_use_generated_implementation() {
        SimpleArithmeticImpl simpleArithmetic = new SimpleArithmeticImpl();

        Assertions.assertThat(simpleArithmetic.add2MultiplyBy3(5)).isEqualTo(21);
        Assertions.assertThat(simpleArithmetic.divideBy5Subtract4Negate(10)).isEqualTo(2);
    }
}