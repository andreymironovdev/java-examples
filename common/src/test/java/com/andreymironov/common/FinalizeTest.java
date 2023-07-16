package com.andreymironov.common;

import com.andreymironov.common.finalizervulnerability.MaliciousResource;
import com.andreymironov.common.finalizervulnerability.Resource;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FinalizeTest {
    @Test
    void should_finalize() {
        Object obj = new Object() {
            @Override
            protected void finalize() throws Throwable {
                System.out.println("Finalize in thread " + Thread.currentThread().getName());
            }
        };

        obj = null;

        System.out.println("Logging GC start in thread " + Thread.currentThread().getName());

        System.gc();
    }

    @Test
    void should_finalize_be_vulnerable_for_attacks() {
        MaliciousResource resource = null;

        try {
            resource = new MaliciousResource();
        } catch (SecurityException ex) {
            // ignore
        }

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(Resource.IMPORTANT_ACTIONS_COUNT.get()).isEqualTo(0);

        System.gc();

        softAssertions.assertThat(Resource.IMPORTANT_ACTIONS_COUNT.get()).isEqualTo(1);

        softAssertions.assertAll();
    }
}
