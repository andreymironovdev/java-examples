package com.andreymironov.yandex.season.backend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class PublicPrivateKeyUtilsTest {
    @Test
    void should_find_simple() {
        Assertions.assertThat(PublicPrivateKeyUtils.getPrivateKeysCount(5, 10)).isEqualTo(2);
    }

    @Test
    void should_not_find_simple() {
        Assertions.assertThat(PublicPrivateKeyUtils.getPrivateKeysCount(10, 11)).isEqualTo(0);
    }

    @Test
    void should_find_medeium() {
        Assertions.assertThat(PublicPrivateKeyUtils.getPrivateKeysCount(527, 9486)).isEqualTo(4);
    }

    @Test
    void should_find_divisors() {
        Assertions.assertThat(PublicPrivateKeyUtils.getPrimeDivisors(24)).containsExactlyInAnyOrderEntriesOf(
                Map.of(2L, 3, 3L, 1)
        );
    }
}