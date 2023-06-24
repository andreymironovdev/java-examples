package com.andreymironov.yandex.season.backend;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

class PublicPrivateKeyUtilsTest {
    private static final List<Long> PRIMES = List.of(
            2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L
    );

    @Test
    void should_find_simple() {
        Assertions.assertThat(PublicPrivateKeyUtils.getPrivateKeysCount(BigInteger.valueOf(5), BigInteger.valueOf(10)))
                .isEqualTo(2);
    }

    @Test
    void should_not_find_simple() {
        Assertions.assertThat(PublicPrivateKeyUtils.getPrivateKeysCount(BigInteger.valueOf(10), BigInteger.valueOf(11)))
                .isEqualTo(0);
    }

    @Test
    void should_not_find_simple_square() {
        Assertions.assertThat(PublicPrivateKeyUtils.getPrivateKeysCount(BigInteger.valueOf(7), BigInteger.valueOf(49)))
                .isEqualTo(2L);
    }

    @Test
    void should_find_medium() {
        Assertions.assertThat(
                        PublicPrivateKeyUtils.getPrivateKeysCount(BigInteger.valueOf(527), BigInteger.valueOf(9486)))
                .isEqualTo(4);
    }

    @Test
    void should_find_for_large_prime() {
        long largePrime = 414507281407L;
        Assertions.assertThat(PublicPrivateKeyUtils.getPrivateKeysCount(BigInteger.valueOf(largePrime),
                        BigInteger.valueOf(2 * largePrime)))
                .isEqualTo(
                        2L
                );
    }

    @Test
    void should_find_for_large_prime_without_iterating_from_1_to_prime() {
        long largePrime = 414507281407L;
        Assertions.assertThat(
                        PublicPrivateKeyUtils.getPrivateKeysCount(BigInteger.ONE, BigInteger.valueOf(2 * largePrime)))
                .isEqualTo(
                        4L
                );
    }

    @Test
    void should_find_arbitrary() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(
                    PublicPrivateKeyUtils.getPrivateKeysCount(
                            BigInteger.valueOf(3L).pow(10).multiply(BigInteger.valueOf(13).pow(7)),
                            BigInteger.valueOf(3L).pow(12).multiply(BigInteger.valueOf(13).pow(10))
                                    .multiply(BigInteger.valueOf(7).pow(5))
                    )
            ).isEqualTo(3 * 4 * 2);
        });
    }
}