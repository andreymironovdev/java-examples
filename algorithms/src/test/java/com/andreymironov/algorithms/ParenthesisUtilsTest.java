package com.andreymironov.algorithms;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class ParenthesisUtilsTest {
    @Test
    void should_be_valid() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(ParenthesisUtils.isValid("()")).isTrue();
            softly.assertThat(ParenthesisUtils.isValid("([])")).isTrue();
            softly.assertThat(ParenthesisUtils.isValid("()[]{}")).isTrue();
            softly.assertThat(ParenthesisUtils.isValid("([{}])")).isTrue();
        });
    }

    @Test
    void should_be_invalid() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(ParenthesisUtils.isValid("(]")).isFalse();
            softly.assertThat(ParenthesisUtils.isValid("(")).isFalse();
            softly.assertThat(ParenthesisUtils.isValid("([)]")).isFalse();
        });
    }
}