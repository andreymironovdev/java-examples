package com.andreymironov.memorytypes;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceTypesTest {
    @Test
    void should_collect_weak_reference() {
        Object strongRef = new Object();
        WeakReference<Object> weakRef = new WeakReference<>(strongRef);
        strongRef = null;

        System.gc();

        Assertions.assertThat(weakRef.get()).isNull();
    }

    @Test
    void should_not_collect_soft_reference() {
        Object strongRef = new Object();
        SoftReference<Object> softRef = new SoftReference<>(strongRef);
        strongRef = null;

        System.gc();

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(softRef.get()).isNotNull();

        try {
            Object[] objs = new Object[10000000];
        }catch (OutOfMemoryError e) {
            //ignored
        }

        softAssertions.assertThat(softRef.get()).isNull();

        softAssertions.assertAll();
    }
}
