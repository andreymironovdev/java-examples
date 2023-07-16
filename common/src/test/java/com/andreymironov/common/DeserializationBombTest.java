package com.andreymironov.common;

import org.assertj.core.api.Assertions;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.*;

public class DeserializationBombTest {
    @Test
    void should_not_deserialize() throws IOException {
        byte[] bomb = bomb();

        AtomicBoolean executed = new AtomicBoolean();

        new Thread(() -> {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bomb));
                objectInputStream.readObject();
                executed.set(true);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Assertions.assertThatCode(() -> await().atMost(5, TimeUnit.SECONDS).untilTrue(executed))
                .isInstanceOf(ConditionTimeoutException.class);
    }

    private static byte[] bomb() throws IOException {
        Set<Object> root = new HashSet<>();
        Set<Object> s1 = root;
        Set<Object> s2 = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Set<Object> t1 = new HashSet<>();
            Set<Object> t2 = new HashSet<>();
            t1.add("foo"); // Make t1 unequal to t2
            s1.add(t1);
            s1.add(t2);
            s2.add(t1);
            s2.add(t2);
            s1 = t1;
            s2 = t2;
        }
        return serialize(root);
    }

    private static byte[] serialize(Set<Object> root) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(root);
        return outputStream.toByteArray();
    }
}
