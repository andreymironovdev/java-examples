package com.andreymironov.algorithms;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class AnagramUtilsTest {
    @Test
    void should_group() {
        Assertions.assertThat(AnagramUtils.groupAnagrams(new String[]{"act", "god", "dog", "cat"}))
                .containsExactlyInAnyOrder(List.of("act", "cat"), List.of("god", "dog"));
    }

    @Test
    void should_group_array_with_collisions_for_basic_sum_hash() {
        Assertions.assertThat(AnagramUtils.groupAnagrams(new String[]{"duh", "ill"}))
                .containsExactlyInAnyOrder(List.of("ill"), List.of("duh"));
    }

    @Test
    void should_see_the_anagram() {
        Assertions.assertThat(AnagramUtils.isAnagram("anagram", "nagaram")).isTrue();
    }

    @Test
    void should_not_see_the_anagram_1() {
        Assertions.assertThat(AnagramUtils.isAnagram("car", "rat")).isFalse();
    }

    @Test
    void should_not_see_the_anagram_2() {
        Assertions.assertThat(AnagramUtils.isAnagram("aac", "bbc")).isFalse();
    }

    @Test
    void should_find_anagrams() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(AnagramUtils.findAnagrams("cbaebabacd", "abc"))
                    .containsExactlyInAnyOrder(0, 6);
            softly.assertThat(AnagramUtils.findAnagrams("abab", "ab"))
                    .containsExactlyInAnyOrder(0, 1, 2);
        });
    }
}