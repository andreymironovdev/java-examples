package com.andreymironov.algorithms;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class RotatedSortedArrayWithDuplicatesSearchUtilsTest {
    @Test
    void should_find_pivot_index() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(
                            RotatedSortedArrayWithDuplicatesSearchUtils.searchPivotIndex(new int[]{1, 0, 1, 1, 1}))
                    .isEqualTo(0);
            softly.assertThat(
                            RotatedSortedArrayWithDuplicatesSearchUtils.searchPivotIndex(new int[]{0, 1, 0, 0, 0}))
                    .isEqualTo(1);
            softly.assertThat(
                            RotatedSortedArrayWithDuplicatesSearchUtils.searchPivotIndex(new int[]{2, 5, 6, 0, 0, 1, 2}))
                    .isEqualTo(2);
            softly.assertThat(
                            RotatedSortedArrayWithDuplicatesSearchUtils.searchPivotIndex(
                                    new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1}
                            ))
                    .isEqualTo(13);
        });
    }

    @Test
    void should_find() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(RotatedSortedArrayWithDuplicatesSearchUtils.search(new int[]{1,0,1,1,1}, 0)).isTrue();
            softly.assertThat(RotatedSortedArrayWithDuplicatesSearchUtils.search(new int[]{2,5,6,0,0,1,2}, 0)).isTrue();
        });
    }
}