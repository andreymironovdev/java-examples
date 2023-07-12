package com.andreymironov.collections.map;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.WeakHashMap;

import static org.awaitility.Awaitility.*;

public class WeakHashMapTest {
    public static class WeakKey {

    }

    @Test
    void should_collect_entry() {
        SoftAssertions softAssertions = new SoftAssertions();

        WeakHashMap<WeakKey, String> map = new WeakHashMap<>();
        WeakKey keyNotToCollect = new WeakKey();
        WeakKey keyToCollect = new WeakKey();

        map.put(keyNotToCollect, "value");
        map.put(keyToCollect, "value");

        softAssertions.assertThat(map.size()).isEqualTo(2);
        softAssertions.assertThat(map.containsKey(keyToCollect)).isTrue();
        softAssertions.assertThat(map.containsKey(keyNotToCollect)).isTrue();

        keyToCollect = null;

        System.gc();

        await().atMost(Duration.of(5, ChronoUnit.SECONDS)).until(() -> map.size() == 1);

        softAssertions.assertThat(map.containsKey(keyNotToCollect)).isTrue();

        softAssertions.assertAll();
    }
}
