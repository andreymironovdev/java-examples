package com.andreymironov.leetcode;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

class SlidingWindowUtilsTest {
    @Test
    void should_find_sliding_window_medians_slow() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(SlidingWindowUtils.medianSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3))
                    .containsExactly(1.0, -1.0, -1.0, 3.0, 5.0, 6.0);
            softAssertions.assertThat(SlidingWindowUtils.medianSlidingWindow(new int[]{1, 2, 3, 4, 2, 3, 1, 4, 2}, 3))
                    .containsExactly(2.0, 3.0, 3.0, 3.0, 2.0, 3.0, 2.0);
            softAssertions.assertThat(
                            SlidingWindowUtils.medianSlidingWindow(new int[]{5, 2, 2, 7, 3, 7, 9, 0, 2, 3}, 9))
                    .containsExactly(3.0, 3.0);
//            softAssertions.assertThat(
//                            SlidingWindowUtils.medianSlidingWindow(
//                                    new int[]{-2147483648, -2147483648, 2147483647, -2147483648, 1, 3, -2147483648, -100, 8, 17, 22, -2147483648, -2147483648, 2147483647, 2147483647, 2147483647, 2147483647, -2147483648, 2147483647, -2147483648},
//                                    6))
//                    .containsExactly(-1073741823.50000, -1073741823.50000, -49.50000, -49.50000, 2.00000, 5.50000,
//                            -46.00000, -46.00000, 12.50000, 19.50000, 1073741834.50000, 2147483647.00000,
//                            2147483647.00000, 2147483647.00000, 2147483647.00000);
        });
    }

    @Test
    void should_find_character_replacement() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(SlidingWindowUtils.characterReplacement("ABAB", 2)).isEqualTo(4);
            softAssertions.assertThat(SlidingWindowUtils.characterReplacement("AABABBA", 1)).isEqualTo(4);
        });
    }
}