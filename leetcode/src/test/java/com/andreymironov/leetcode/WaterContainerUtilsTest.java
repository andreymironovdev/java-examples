package com.andreymironov.leetcode;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class WaterContainerUtilsTest {
    @Test
    void should_find_max_area() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(WaterContainerUtils.findMaxWaterArea(new int[]{1, 0, 4, 5, 3, 2}))
                    .isEqualTo(6);
        });
    }
}