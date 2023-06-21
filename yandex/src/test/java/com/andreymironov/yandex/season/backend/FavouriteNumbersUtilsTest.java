package com.andreymironov.yandex.season.backend;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

class FavouriteNumbersUtilsTest {
    @Test
    void should_calc() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(FavouriteNumbersUtils.getProbability(21, 1))
                    .isEqualTo(1.0);
            softAssertions.assertThat(FavouriteNumbersUtils.getProbability(145, 2))
                    .isCloseTo(0.333, Offset.strictOffset(0.001));
        });
    }
}
