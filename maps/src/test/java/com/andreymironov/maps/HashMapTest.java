package com.andreymironov.maps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

public class HashMapTest {
    @Test
    void should_be_equal_by_contents() {
        HashMap<String, String> map1 = new HashMap<>();
        HashMap<String, String> map2 = new HashMap<>();

        for (String key: List.of("key1", "key2")) {
            for (String value: List.of("value1", "value2")) {
                map1.put(key, value);
                map2.put(key, value);
            }
        }

        Assertions.assertTrue(map1.equals(map2));
    }
}
