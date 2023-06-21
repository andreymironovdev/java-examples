package com.andreymironov.yandex;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class CheapestWayUtilsTest {
    @Test
    void should_calc() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(CheapestWayUtils.getMinPrice(new int[][]{
                    new int[]{1, 1, 1, 1, 1},
                    new int[]{3, 100, 100, 100, 100},
                    new int[]{1, 1, 1, 1, 1},
                    new int[]{2, 2, 2, 2, 1},
                    new int[]{1, 1, 1, 1, 1},
            })).isEqualTo(11);
        });
    }
}