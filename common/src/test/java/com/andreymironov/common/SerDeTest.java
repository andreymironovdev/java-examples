package com.andreymironov.common;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class SerDeTest {
    @Test
    void should_not_serialize() throws IOException {
        Assertions.assertThatCode(() -> new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(new Object()))
                .isInstanceOf(NotSerializableException.class);
    }

    @Test
    void should_serde() throws IOException, ClassNotFoundException {
        User user = new User("John", "Doe", 23);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(user);

        byte[] bytes = byteArrayOutputStream.toByteArray();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        User userDeserialized = (User) objectInputStream.readObject();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(userDeserialized.name).isEqualTo(user.name);
            softAssertions.assertThat(userDeserialized.surName).isEqualTo(user.surName);
            softAssertions.assertThat(userDeserialized.age).isEqualTo(user.age);
        });
    }

    private static class User implements Serializable {
        String name;
        String surName;
        int age;

        public User(String name, String surName, int age) {
            this.name = name;
            this.surName = surName;
            this.age = age;
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeObject(name);
            out.writeInt(666);
            out.writeObject(surName);
            out.writeInt(age);
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            name = (String) in.readObject();
            in.readInt();
            surName = (String) in.readObject();
            age = in.readInt();
        }
    }
}
