package com.andreymironov.generics;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RuntimeTypesTest {
    @Test
    void should_runtime_types_be_equal() {
        List<String> stringList = new ArrayList<>(List.of("string"));
        List<Integer> integerList = new ArrayList<>(List.of(1));

        SoftAssertions.assertSoftly(softly ->{
            softly.assertThat(stringList.getClass().getName()).isEqualTo(ArrayList.class.getName());
            softly.assertThat(integerList.getClass().getName()).isEqualTo(ArrayList.class.getName());
        });
    }
}
