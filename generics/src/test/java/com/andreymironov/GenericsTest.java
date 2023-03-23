package com.andreymironov;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GenericsTest {
    @Test
    void should_cast_lead_to_heap_pollution() {
        List<?> stringList = new ArrayList<>(List.of("string"));
        List<Integer> integerList = (List<Integer>) stringList;
        integerList.add(1);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThatCode(() -> Integer.class.cast(integerList.get(0))).isInstanceOf(ClassCastException.class);
            softly.assertThatCode(() -> integerList.get(0)).doesNotThrowAnyException();
            softly.assertThatCode(() -> Integer.class.cast(integerList.get(1))).doesNotThrowAnyException();
        });
    }

    @Test
    void should_equal_runtime_types() {
        List<String> stringList = new ArrayList<>(List.of("string"));
        List<Integer> integerList = new ArrayList<>(List.of(1));

        SoftAssertions.assertSoftly(softly ->{
            softly.assertThat(stringList.getClass()).isEqualTo(ArrayList.class);
            softly.assertThat(integerList.getClass()).isEqualTo(ArrayList.class);
        });
    }
}
