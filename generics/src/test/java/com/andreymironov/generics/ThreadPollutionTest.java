package com.andreymironov.generics;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ThreadPollutionTest {
    @Test
    void should_cast_lead_to_heap_pollution() {
        List<?> stringList = new ArrayList<>(List.of("string"));
        List<Integer> integerList = (List<Integer>) stringList;
        integerList.add(1);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(integerList.size()).isEqualTo(2);
            softly.assertThatCode(() -> integerList.get(0).compareTo(0)).isInstanceOf(ClassCastException.class);
            softly.assertThatCode(() -> integerList.get(1).compareTo(0)).doesNotThrowAnyException();
        });
    }
}
