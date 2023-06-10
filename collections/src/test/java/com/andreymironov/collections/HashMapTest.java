package com.andreymironov.collections;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class HashMapTest {
    private SoftAssertions softAssertions;

    @BeforeEach
    public void beforeEach() {
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    public void afterEach() {
        softAssertions.assertAll();
    }

    @Test
    public void testHashCode() {
        HashMap<CustomKey, String> map = new HashMap<>();
        map.put(new CustomKey(), "value1");
        map.put(new CustomKey(), "value2");
        softAssertions.assertThat(map.size()).isEqualTo(1);
        softAssertions.assertThat(map.get(new CustomKey())).isEqualTo("value2");
    }

    @RequiredArgsConstructor
    private static class CustomKey {
        @Override
        public boolean equals(Object other) {
            return true;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }
}
