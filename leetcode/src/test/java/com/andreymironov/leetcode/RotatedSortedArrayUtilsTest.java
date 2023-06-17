package com.andreymironov.leetcode;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class RotatedSortedArrayUtilsTest {
    @Test
    void should_search_pivot_index() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(RotatedSortedArrayUtils.searchPivotIndex(new int[]{1, 2, 0})).isEqualTo(1);
            softly.assertThat(RotatedSortedArrayUtils.searchPivotIndex(new int[]{2, 0, 1})).isEqualTo(0);
            softly.assertThat(RotatedSortedArrayUtils.searchPivotIndex(new int[]{0, 1, 2})).isEqualTo(-1);
            softly.assertThat(RotatedSortedArrayUtils.searchPivotIndex(new int[]{6, 7, 8, 1, 2, 3, 4, 5}))
                    .isEqualTo(2);
        });
    }

    @Test
    void should_search_in_rotated_sorted_array_without_duplicates() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(RotatedSortedArrayUtils.searchWithoutDuplicates(new int[]{1, 2, 3, 0}, 1)).isEqualTo(0);
            softly.assertThat(RotatedSortedArrayUtils.searchWithoutDuplicates(new int[]{1, 2, 3, 0}, 2)).isEqualTo(1);
            softly.assertThat(RotatedSortedArrayUtils.searchWithoutDuplicates(new int[]{1, 2, 3, 0}, 3)).isEqualTo(2);
            softly.assertThat(RotatedSortedArrayUtils.searchWithoutDuplicates(new int[]{1, 2, 3, 0}, 0)).isEqualTo(3);
            softly.assertThat(RotatedSortedArrayUtils.searchWithoutDuplicates(new int[]{6, 7, 8, 1, 2, 3, 4, 5}, 6))
                    .isEqualTo(0);
        });
    }

    @Test
    void should_search_with_duplicates() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(RotatedSortedArrayUtils.searchWithDuplicates(new int[]{1, 0, 1, 1, 1}, 0)).isTrue();
            softly.assertThat(RotatedSortedArrayUtils.searchWithDuplicates(new int[]{2, 5, 6, 0, 0, 1, 2}, 0)).isTrue();
        });
    }
}