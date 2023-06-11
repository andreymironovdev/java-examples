package com.andreymironov.algorithms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class SearchSumInArrayUtilsTest {
    @Test
    void should_search_0_sum() {
        Assertions.assertThat(SearchSumInArrayUtils.search4Sums(new int[]{1, 0, -1, 0, -2, 2}, 0))
                .containsExactlyInAnyOrder(
                        List.of(-2, -1, 1, 2),
                        List.of(-2, 0, 0, 2),
                        List.of(-1, 0, 0, 1));
    }

    @Test
    void should_search_in_duplicates() {
        Assertions.assertThat(SearchSumInArrayUtils.search4Sums(new int[]{2, 2, 2, 2, 2}, 8))
                .containsExactlyInAnyOrder(List.of(2, 2, 2, 2));
    }

    @Test
    void should_not_search_outside_of_int_range() {
        Assertions.assertThat(
                        SearchSumInArrayUtils.search4Sums(new int[]{1000000000, 1000000000, 1000000000, 1000000000}, -294967296
                        ))
                .isEmpty();
    }
}