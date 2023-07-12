package com.andreymironov.collections.queue;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PriorityQueueTest {
    @Test
    void should_iteration_order_be_different_from_priority_order() {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        List<Integer> content = List.of(1, 3, 2);
        queue.addAll(content);
        List<Integer> iterationOrder = queue.stream().toList();

        SoftAssertions softAssertions = new SoftAssertions();

        List<Integer> sorted = content.stream().sorted().collect(Collectors.toList());

        softAssertions.assertThat(iterationOrder)
                .doesNotContainSequence(sorted);
        softAssertions.assertThat(IntStream.range(0, queue.size()).map(i -> queue.poll()).boxed().toList())
                .containsSequence(sorted);
        softAssertions.assertAll();
    }
}
