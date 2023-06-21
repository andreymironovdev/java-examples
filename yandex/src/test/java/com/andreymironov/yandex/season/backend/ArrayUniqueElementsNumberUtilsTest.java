package com.andreymironov.yandex.season.backend;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class ArrayUniqueElementsNumberUtilsTest {
    @Test
    void should_count() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(
                            ArrayUniqueElementsNumberUtils.getUniqueElementsNumber(new int[]{9, 3, 10, 5, 7, 6, 4, 1, 2, 8}))
                    .isEqualTo(10);
        });
    }
}