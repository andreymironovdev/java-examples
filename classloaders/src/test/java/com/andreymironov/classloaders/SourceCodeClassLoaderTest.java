package com.andreymironov.classloaders;

import com.andreymironov.classloaders.sourcecodeclassloader.SourceCodeClassLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class SourceCodeClassLoaderTest {
    @Test
    void should_load_class_by_source_code_from_classpath()
            throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        SourceCodeClassLoader sourceCodeClassLoader = new SourceCodeClassLoader();
        Class<?> loadedClass = sourceCodeClassLoader.loadClass(SourceCodeClassLoader.FULL_CLASS_NAME);
        Assertions.assertThat(loadedClass.getDeclaredMethod("returnOK").invoke(null)).isEqualTo("OK");
    }
}
