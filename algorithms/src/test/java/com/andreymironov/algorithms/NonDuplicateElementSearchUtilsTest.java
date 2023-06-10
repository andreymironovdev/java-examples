package com.andreymironov.algorithms;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class NonDuplicateElementSearchUtilsTest {
    @Test
    void should_find() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(NonDuplicateElementSearchUtils.getNonDuplicate(new int[]{1, 2, 3, 4, 3, 2, 1}))
                    .isEqualTo(4);
        });
    }
}