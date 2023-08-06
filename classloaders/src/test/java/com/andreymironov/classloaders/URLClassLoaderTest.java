package com.andreymironov.classloaders;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class URLClassLoaderTest {
    @Test
    public void should_load_guava_by_url() throws MalformedURLException, ClassNotFoundException {
        SoftAssertions softAssertions = new SoftAssertions();

        URL url = new URL("https://repo1.maven.org/maven2/com/google/guava/guava/31.1-jre/guava-31.1-jre.jar");
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
        softAssertions.assertThat(urlClassLoader.getParent()).isEqualTo(ClassLoader.getSystemClassLoader());

        Class<?> immutableListClass = urlClassLoader.loadClass("com.google.common.collect.ImmutableList");
        softAssertions.assertThat(immutableListClass).isNotNull();
        softAssertions.assertThat(immutableListClass.getClassLoader()).isEqualTo(urlClassLoader);

        softAssertions.assertAll();
    }
}
