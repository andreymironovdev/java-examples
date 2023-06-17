package com.andreymironov.leetcode;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class MatrixUtilsTest {
    @Test
    void should_find_islands() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(MatrixUtils.getIslandsCount(new char[][]{
                            {'1', '0'},
                            {'0', '1'}
                    }))
                    .isEqualTo(2);
            softly.assertThat(MatrixUtils.getIslandsCount(new char[][]{
                            {'1', '1', '1', '1', '0'},
                            {'1', '1', '0', '1', '0'},
                            {'1', '1', '0', '0', '0'},
                            {'0', '0', '0', '0', '0'}
                    }))
                    .isEqualTo(1);
            softly.assertThat(MatrixUtils.getIslandsCount(new char[][]{
                            {'1', '1', '0', '0', '0'},
                            {'1', '1', '0', '0', '0'},
                            {'0', '0', '1', '0', '0'},
                            {'0', '0', '0', '1', '1'}
                    }))
                    .isEqualTo(3);
        });
    }
}