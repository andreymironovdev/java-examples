package com.andreymironov.common;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class FloatingPointCalculationsTest {
    @Test
    void should_double_not_be_exact() {
        SoftAssertions softAssertions = new SoftAssertions();

        double calculation = 1.0 - 9 * 0.1;
        double expected = 0.1;
        double actual = 0.09999999999999998;

        softAssertions.assertThat(calculation).isNotEqualTo(expected);
        softAssertions.assertThat(calculation).isEqualTo(actual);

        softAssertions.assertAll();
    }

    @Test
    void should_big_decimal_be_exact() {
        SoftAssertions softAssertions = new SoftAssertions();

        BigDecimal calculation = BigDecimal.ONE.subtract(BigDecimal.valueOf(9).multiply(BigDecimal.valueOf(0.1)));
        BigDecimal expected = BigDecimal.valueOf(0.1);

        softAssertions.assertThat(calculation).isEqualTo(expected);

        softAssertions.assertAll();
    }
}
