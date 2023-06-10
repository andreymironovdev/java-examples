package com.andreymironov.algorithms;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class RotatedSortedArrayWithoutDuplicatesSearchUtilsTest {
    @Test
    void should_search_pivot_index() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(RotatedSortedArrayWithoutDuplicatesSearchUtils.searchPivotIndex(new int[]{1, 2, 0})).isEqualTo(1);
            softly.assertThat(RotatedSortedArrayWithoutDuplicatesSearchUtils.searchPivotIndex(new int[]{2, 0, 1})).isEqualTo(0);
            softly.assertThat(RotatedSortedArrayWithoutDuplicatesSearchUtils.searchPivotIndex(new int[]{0, 1, 2})).isEqualTo(-1);
            softly.assertThat(RotatedSortedArrayWithoutDuplicatesSearchUtils.searchPivotIndex(new int[]{6, 7, 8, 1, 2, 3, 4, 5}))
                    .isEqualTo(2);
        });
    }

    @Test
    void should_search_in_rotated_sorted_array() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(RotatedSortedArrayWithoutDuplicatesSearchUtils.search(new int[]{1, 2, 3, 0}, 1)).isEqualTo(0);
            softly.assertThat(RotatedSortedArrayWithoutDuplicatesSearchUtils.search(new int[]{1, 2, 3, 0}, 2)).isEqualTo(1);
            softly.assertThat(RotatedSortedArrayWithoutDuplicatesSearchUtils.search(new int[]{1, 2, 3, 0}, 3)).isEqualTo(2);
            softly.assertThat(RotatedSortedArrayWithoutDuplicatesSearchUtils.search(new int[]{1, 2, 3, 0}, 0)).isEqualTo(3);
            softly.assertThat(RotatedSortedArrayWithoutDuplicatesSearchUtils.search(new int[]{6, 7, 8, 1, 2, 3, 4, 5}, 6)).isEqualTo(0);
        });
    }
}