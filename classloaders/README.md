In Java, classloaders are responsible for loading classes at runtime. They are a fundamental part of the Java Virtual
Machine (JVM) and play a crucial role in dynamically loading classes when they are needed. Java provides a hierarchical
classloader system that follows the parent delegation model, allowing multiple classloaders to work together.

1. Bootstrap Classloader:
    - The Bootstrap classloader is the root of the classloader hierarchy and is responsible for loading core Java
      classes, such as those found in the `java.lang` package. It is implemented in native code and is not represented
      by a Java class.

2. Extension Classloader:
    - The Extension classloader is a child of the Bootstrap classloader and is responsible for loading classes from the
      JDK's extension directories (`$JAVA_HOME/lib/ext`).

3. System Classloader (Application Classloader):
    - The System classloader is a child of the Extension classloader and is responsible for loading classes from the
      application's classpath. It loads classes from the directories and JAR files specified in the `CLASSPATH`
      environment variable or the `-classpath` (or `-cp`) command-line option.

4. Custom Classloaders:
    - Developers can create custom classloaders by extending the `ClassLoader` class. Custom classloaders can be used to
      load classes from different sources, such as databases, network locations, or encrypted archives.
