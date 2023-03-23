package com.andreymironov.memorytypes;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    protected SoftAssertions softAssertions;

    @BeforeEach
    void beforeEach() {
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    void afterEach() {
        softAssertions.assertAll();
    }

}
