package com.andreymironov.classsloaders;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ClassloadersTest {
    @Test
    public void testBootstrapClassloader() {
        SoftAssertions softAssertions = new SoftAssertions();

        List<Class<?>> someJdkInternalClasses = List.of(List.class, String.class, ClassLoader.class);
        System.out.println("Check the classloader of some JDK internal classes: " + someJdkInternalClasses);
        someJdkInternalClasses.forEach(clazz -> {
            ClassLoader bootstrapClassloader = clazz.getClassLoader();
            softAssertions.assertThat(bootstrapClassloader).isNull();
        });

        System.out.println("""
                It is called the bootstrap classloader and it's mainly responsible for loading JDK internal classes,
                typically rt.jar and other core libraries located in the $JAVA_HOME/jre/lib directory.
                Additionally, the Bootstrap class loader serves as the parent of all the other ClassLoader instances.
                It is null, because the bootstrap class loader is written in native code, not Java, so it doesn't
                show up as a Java class.
                As a result, the behavior of the bootstrap class loader will differ across JVMs.
                """);

        softAssertions.assertAll();
    }

    @Test
    public void testExtensionClassloader() {
        System.out.println("""
                The extension class loader is a child of the bootstrap class loader, and takes care
                of loading the extensions of the standard core Java classes so that they're available
                to all applications running on the platform.
                The extension class loader loads from the JDK extensions directory, usually the
                $JAVA_HOME/lib/ext directory, or any other directory mentioned in the java.ext.dirs system property.
                The class of extension classloader is %s.
                """.formatted(ClassLoader.getSystemClassLoader().getParent().getClass()));
    }

    @Test
    public void testSystemClassloader() {
        SoftAssertions softAssertions = new SoftAssertions();

        List<Class<?>> someAppClasses = List.of(this.getClass(), Assertions.class, Test.class);
        System.out.println("Check classloader of the following classes from classpath: " + someAppClasses);
        someAppClasses.forEach(clazz -> {
            ClassLoader appClassloader = clazz.getClassLoader();
            softAssertions.assertThat(appClassloader).isNotNull();
            softAssertions.assertThat(appClassloader).isEqualTo(ClassLoader.getSystemClassLoader());
        });

        System.out.println("""
                The system or application class loader takes care of loading all the application level classes into the JVM.
                It loads files found in the classpath environment variable, -classpath, or -cp command line option.
                It's also a child of the extensions class loader.
                The implementation is JVM specific, the name of the current JVM app classloader
                class is %s.
                """.formatted(ClassLoader.getSystemClassLoader().getClass().getName()));

        softAssertions.assertAll();
    }
}