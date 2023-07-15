package com.andreymironov.classloaders;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class ClassloadersTest {
    private SoftAssertions softAssertions;

    @BeforeEach
    void beforeEach() {
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    void afterEach() {
        softAssertions.assertAll();
    }

    @Test
    public void testBootstrapClassloader() {
        List<Class<?>> someJdkInternalClasses = List.of(List.class, String.class, ClassLoader.class);
        System.out.println("Check the classloader of some JDK internal classes: " + someJdkInternalClasses);
        someJdkInternalClasses.forEach(clazz -> {
            ClassLoader bootstrapClassloader = clazz.getClassLoader();
            softAssertions.assertThat(bootstrapClassloader).isNull();
        });

        System.out.println("""
                It is called the bootstrap or primordial classloader and it's mainly responsible for loading JDK
                internal classes, typically rt.jar and other core libraries located in the $JAVA_HOME/jre/lib directory.
                Additionally, the Bootstrap class loader serves as the parent of all the other ClassLoader instances.
                It is shown as null, because the bootstrap class loader is written in native code, not Java, so it
                doesn't show up as a Java class.
                As a result, the behavior of the bootstrap class loader will differ across JVMs.
                """);
    }

    @Test
    public void testExtensionClassloader() {
        System.out.println("""
                The extension class loader is a child of the bootstrap class loader.
                It takes care of loading the extensions of the standard core Java classes so that they're available
                to all applications running on the platform.
                The extension class loader loads from the JDK extensions directory, usually the
                $JAVA_HOME/lib/ext directory, or any other directory mentioned in the java.ext.dirs system property.
                The class of extension classloader is %s.
                """.formatted(ClassLoader.getSystemClassLoader().getParent().getClass()));
    }

    @Test
    public void testSystemClassloader() {
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
    }

    @Test
    public void testContextClassLoader() throws InterruptedException {
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
            softAssertions.assertThat(classLoader).isEqualTo(contextClassLoader);
        };

        Thread thread = new Thread(runnable);
        thread.setContextClassLoader(contextClassLoader);
        thread.start();
        thread.join();

        System.out.println("""
                Context class loaders are significant when required resources aren’t present on the classpath of the
                default Java class loaders. Therefore, we can use context class loaders to diverge from the traditional
                linear delegation model.
                In the hierarchical model of class loaders, resources loaded by parent class loaders are visible to the
                child class loaders, but not vice versa. In some scenarios, parent class loaders might need to access
                classes present on the classpath of child class loaders.
                Context class loaders are a useful tool to make this happen. We can set the context class loader to the
                desired value when accessing required resources. Hence, in the above case, we can use the child
                thread’s context class loader and can locate the resources present at the child class loader level.
                """);
    }

    @Test
    public void testUrlClassLoader() throws MalformedURLException, ClassNotFoundException {
        URL url = new URL("https://repo1.maven.org/maven2/com/google/guava/guava/31.1-jre/guava-31.1-jre.jar");
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
        softAssertions.assertThat(urlClassLoader.getParent()).isEqualTo(ClassLoader.getSystemClassLoader());
        Class<?> immutableListClass = urlClassLoader.loadClass("com.google.common.collect.ImmutableList");
        softAssertions.assertThat(immutableListClass).isNotNull();
        System.out.println("""
                The following class was loaded by created URL class loader: %s.
                The parent of this class loader is System class loader.
                """.formatted(immutableListClass));
        softAssertions.assertThat(immutableListClass.getClassLoader()).isEqualTo(urlClassLoader);
    }
}