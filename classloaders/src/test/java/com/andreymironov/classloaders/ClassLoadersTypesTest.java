package com.andreymironov.classloaders;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ClassLoadersTypesTest {
    @Test
    public void should_bootstrap_classloader_be_null() {
        SoftAssertions.assertSoftly(softAssertions -> {
            List<Class<?>> someJdkInternalClasses = List.of(List.class, String.class, ClassLoader.class);
            someJdkInternalClasses.forEach(clazz -> {
                ClassLoader bootstrapClassloader = clazz.getClassLoader();
                softAssertions.assertThat(bootstrapClassloader).isNull();
            });
        });
    }

    @Test
    public void testExtensionClassloader() {
        ClassLoader extensionClassLoader = ClassLoader.getSystemClassLoader().getParent();
        Assertions.assertThat(extensionClassLoader).isNotNull();

        System.out.printf("""
                Extension classloader class is %s
                """, extensionClassLoader.getClass());
    }

    @Test
    public void should_app_classes_be_loaded_by_system_class_loader() {
        SoftAssertions softAssertions = new SoftAssertions();

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        softAssertions.assertThat(systemClassLoader).isNotNull();

        List<Class<?>> someAppClasses = List.of(this.getClass(), Test.class);
        someAppClasses.forEach(clazz -> {
            softAssertions.assertThat(clazz.getClassLoader()).isEqualTo(systemClassLoader);
        });

        softAssertions.assertAll();


        System.out.printf("""
                System (or application) classloader class is %s
                """, systemClassLoader.getClass());
    }

    @Test
    public void should_new_thread_use_context_classloader() throws InterruptedException {
        ClassLoader contextClassLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                return super.loadClass(name);
            }
        };

        Runnable runnable = () -> {
            Thread thread = Thread.currentThread();
            System.out.println("Check context classloader of thread instance: " + thread);
            ClassLoader classLoader = thread.getContextClassLoader();
            Assertions.assertThat(classLoader).isEqualTo(contextClassLoader);
        };

        Thread thread = new Thread(runnable);
        thread.setContextClassLoader(contextClassLoader);
        thread.start();
        thread.join();
    }
}