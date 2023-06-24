package com.andreymironov.yandex.season.backend;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class SquareAndCircleUtilsTest {
    @Test
    void should_calc_for_1_point() {
        Assertions.assertThat(SquareAndCircleUtils.getExpectedValue(new double[][]{
                new double[]{0.5, 0.5}
        }, 0.5)).isCloseTo(BigDecimal.valueOf(0.7853981634), Offset.offset(BigDecimal.valueOf(0.000000001)));
    }

    @Test
    void should_calc_for_2_points() {
        Assertions.assertThat(SquareAndCircleUtils.getExpectedValue(new double[][]{
                new double[]{0.001, 0.001},
                new double[]{0.999, 0.999}
        }, 2)).isCloseTo(BigDecimal.valueOf(2), Offset.offset(BigDecimal.valueOf(0.000000001)));
    }
}