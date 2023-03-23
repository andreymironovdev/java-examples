package com.andreymironov.memorytypes;

import org.junit.jupiter.api.Test;

public class StackMemoryTest extends BaseTest {
    private int times;

    @Test
    public void should_cause_stackoverflow_exception() {
        System.out.println("The number before Stack overflowing: " + times);
        softAssertions.assertThatThrownBy(this::recursiveInvoke).isInstanceOf(StackOverflowError.class);
        System.out.println("The number after Stack overflowing: " + times);
    }

    private void recursiveInvoke() {
        this.times++;
        recursiveInvoke();
    }
}