package com.andreymironov.yandex.season.backend;

import com.andreymironov.yandex.season.backend.CheckersUtils.Coordinates;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CheckersUtilsTest {
    @Test
    void should_find_beat() {
        Assertions.assertThat(CheckersUtils.canBeat(8, 8,
                new Coordinates[]{new Coordinates(1, 1), new Coordinates(2, 6), new Coordinates(6, 6)},
                new Coordinates[]{new Coordinates(2, 2), new Coordinates(7, 7), new Coordinates(8, 8)},
                true)).isTrue();
    }

    @Test
    void should_not_find_beat() {
        Assertions.assertThat(CheckersUtils.canBeat(8, 8,
                new Coordinates[]{new Coordinates(2,2), new Coordinates(3,3)},
                new Coordinates[]{new Coordinates(7,7), new Coordinates(8,8)},
                false)).isFalse();
    }
}