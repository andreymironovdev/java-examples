package com.andreymironov.leetcode;

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

    @Test
    void should_remove_parenthesis() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(ParenthesisUtils.removeInvalidParentheses("()())()"))
                    .containsExactlyInAnyOrder("(())()", "()()()");
            softAssertions.assertThat(ParenthesisUtils.removeInvalidParentheses("(a)())()"))
                    .containsExactlyInAnyOrder("(a())()", "(a)()()");
            softAssertions.assertThat(ParenthesisUtils.removeInvalidParentheses(")("))
                    .containsExactlyInAnyOrder("");
        });
    }
}