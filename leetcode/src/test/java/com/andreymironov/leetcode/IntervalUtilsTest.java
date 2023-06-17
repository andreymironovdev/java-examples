package com.andreymironov.leetcode;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class IntervalUtilsTest {
    @Test
    void should_merge() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(IntervalUtils.merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}}))
                    .isDeepEqualTo(new int[][]{{1, 6}, {8, 10}, {15, 18}});
            softAssertions.assertThat(IntervalUtils.merge(new int[][]{{1, 4}, {4, 5}}))
                    .isDeepEqualTo(new int[][]{{1, 5}});
        });
    }
}