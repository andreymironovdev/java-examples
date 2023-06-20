package com.andreymironov.yandex;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class PinsUtilsTest {
    @Test
    void should_find_min_length() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(PinsUtils.getMinLength(new int[]{27, 3, 8, 4, 1})).isEqualTo(22);
            softAssertions.assertThat(PinsUtils.getMinLength(new int[]{27, 3, 8, 7, 1})).isEqualTo(22);
            softAssertions.assertThat(PinsUtils.getMinLength(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}))
                    .isEqualTo(5);
        });
    }
}