package com.andreymironov.classloaders;

import com.andreymironov.classloaders.lazyinitialization.ResourceFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LazyInitializationTest {
    @Test
    void should_lazy_initialize_by_holder_class()
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        SoftAssertions softAssertions = new SoftAssertions();

        String resourceFactoryClassName = "com.andreymironov.classloaders.lazyinitialization.ResourceFactory";
        String resourceHolderClassName = resourceFactoryClassName + "$ResourceHolder";

        softAssertions.assertThat(getLoadedClass(resourceFactoryClassName)).isNull();
        softAssertions.assertThat(getLoadedClass(resourceHolderClassName)).isNull();

        ResourceFactory.triggerClassLoad();
        softAssertions.assertThat(getLoadedClass(resourceFactoryClassName)).isNotNull();
        softAssertions.assertThat(getLoadedClass(resourceHolderClassName)).isNull();

        softAssertions.assertThat(ResourceFactory.getResource()).isNotNull();

        softAssertions.assertThat(getLoadedClass(resourceHolderClassName)).isNotNull();

        softAssertions.assertAll();
    }

    private Class<?> getLoadedClass(String name)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
        m.setAccessible(true);
        return (Class<?>) m.invoke(this.getClass().getClassLoader(), name);
    }
}
