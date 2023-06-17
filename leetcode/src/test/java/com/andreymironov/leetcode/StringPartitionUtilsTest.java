package com.andreymironov.leetcode;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class StringPartitionUtilsTest {
    @Test
    void should_partition() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(StringPartitionUtils.splitStringWithSymbolsUniqueness("abca"))
                    .containsExactly(4);
            softAssertions.assertThat(StringPartitionUtils.splitStringWithSymbolsUniqueness("abcdef"))
                    .containsExactly(1, 1, 1, 1, 1, 1);
            softAssertions.assertThat(StringPartitionUtils.splitStringWithSymbolsUniqueness("ababcbacadefegdehijhklij"))
                    .containsExactly(9, 7, 8);
        });
    }
}