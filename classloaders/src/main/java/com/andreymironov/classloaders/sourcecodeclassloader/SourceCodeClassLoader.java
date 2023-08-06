package com.andreymironov.classloaders.sourcecodeclassloader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

/**
 * This classloader loads copies source code of a java class to the temporary directory, compiles it
 * and loads compiled class using parent URLClassLoader
 */
public class SourceCodeClassLoader extends URLClassLoader {
    public static final String FULL_CLASS_NAME = "com.andreymironov.classloaders.sourcecodeclassloader.ClassFromResources";
    private static final String SOURCE_FILE_NAME = "ClassFromResources.java";

    private static final File CLASSPATH;

    static {
        try {
            CLASSPATH = Files.createTempDirectory("java").toFile();
            System.out.println("Classpath folder is " + CLASSPATH.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SourceCodeClassLoader() throws IOException {
        super(new URL[]{CLASSPATH.toURI().toURL()});
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.equals(FULL_CLASS_NAME)) {
            compileSourceCode();
        }

        return super.findClass(name);
    }

    private void compileSourceCode() {
        try {
            byte[] bytes = this.getResourceAsStream(SOURCE_FILE_NAME).readAllBytes();
            File source = new File(CLASSPATH, FULL_CLASS_NAME.replaceAll("\\.", "/") + ".java");
            source.getParentFile().mkdirs();
            Files.write(source.toPath(), bytes);

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int exitCode = compiler.run(null, null, null, source.getPath());
            if (exitCode != 0) {
                throw new RuntimeException("Compilation error!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * For testing class loader when running jar
     */
    public static void main(String[] args)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        SourceCodeClassLoader sourceCodeClassLoader = new SourceCodeClassLoader();
        Class<?> loadedClass = sourceCodeClassLoader.loadClass(SourceCodeClassLoader.FULL_CLASS_NAME);
        System.out.println(loadedClass.getDeclaredMethod("returnOK").invoke(null));
    }
}
