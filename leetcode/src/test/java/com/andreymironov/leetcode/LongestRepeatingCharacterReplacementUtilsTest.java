package com.andreymironov.leetcode;

import com.andreymironov.leetcode.LongestRepeatingCharacterReplacementUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class LongestRepeatingCharacterReplacementUtilsTest {
    @Test
    void should_find() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(LongestRepeatingCharacterReplacementUtils.characterReplacement("ABAB", 2)).isEqualTo(4);
            softAssertions.assertThat(LongestRepeatingCharacterReplacementUtils.characterReplacement("AABABBA", 1)).isEqualTo(4);
        });
    }
}