package com.andreymironov.instrumentation;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class JavassistMethodTransformer implements ClassFileTransformer {
    private final String classNameToInstrument;
    private final String methodNameToInstrument;

    public JavassistMethodTransformer(String classNameToInstrument, String methodNameToInstrument) {
        this.classNameToInstrument = classNameToInstrument;
        this.methodNameToInstrument = methodNameToInstrument;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        String classNameWithDots = className.replaceAll("/", ".");

        if (classNameWithDots.equals(classNameToInstrument)) {
            return transform();
        }

        return classfileBuffer;
    }

    private byte[] transform() {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.getOrNull(classNameToInstrument);

//            When java agent is loaded at runtime, this thread's context classloader is null (bootstrap),
//            so we need to manually add target class loader class path to javassist's class pool
//            System.out.println(Thread.currentThread().getContextClassLoader());
            if (ctClass == null) {
                pool.appendClassPath(new LoaderClassPath(Class.forName(classNameToInstrument).getClassLoader()));
                ctClass = pool.getCtClass(classNameToInstrument);
            }

            CtMethod methodToInstrument = ctClass.getDeclaredMethod(methodNameToInstrument);
            methodToInstrument.insertBefore(
                    String.format("System.out.println(\"Successfully instrumented method %s - inserted sout at the start!\");",
                            methodNameToInstrument));

            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
