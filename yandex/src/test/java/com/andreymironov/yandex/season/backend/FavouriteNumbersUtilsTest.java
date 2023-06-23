package com.andreymironov.yandex.season.backend;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

class FavouriteNumbersUtilsTest {
    @Test
    void should_calc_simple_cases() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(FavouriteNumbersUtils.getProbability(BigInteger.valueOf(21), 1))
                    .isCloseTo(BigDecimal.ONE, Offset.offset(BigDecimal.ZERO));
            softAssertions.assertThat(FavouriteNumbersUtils.getProbability(BigInteger.valueOf(51), 1))
                    .isCloseTo(BigDecimal.ONE, Offset.offset(BigDecimal.ZERO));

        });
    }

    @Test
    void should_calc_for_3_ciphers_number() {
        Assertions.assertThat(FavouriteNumbersUtils.getProbability(BigInteger.valueOf(145), 2))
                .isCloseTo(BigDecimal.valueOf(0.333333333), Offset.strictOffset(BigDecimal.valueOf(0.000000001)));
    }

    @Test
    void should_calc_for_long_number() {
        Assertions.assertThat(FavouriteNumbersUtils.getProbability(BigInteger.valueOf(9472558923575L), 7))
                .isCloseTo(BigDecimal.valueOf(0.50090267885), Offset.strictOffset(BigDecimal.valueOf(0.000000001)));
    }
}

