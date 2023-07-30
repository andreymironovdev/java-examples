package com.andreymironov.instrumentation;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class MethodsClassFileTransformer implements ClassFileTransformer {
    private String classNameToFind = "com.andreymironov.instrumentation.consumer.Main";
    private String methodNameToReplaceBody = "methodToReplaceBody";
    private String methodNameToInsertBeforeBody = "methodToInsertBeforeBody";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        System.out.printf("Invoked methods transformer on class %s and classloader %s%n", className,
                loader.getClass().getName());

        if (className.equals(classNameToFind.replace(".", "/"))) {
            return transform(className);
        }

        return null;
    }

    private byte[] transform(String className) {
        try {
            System.out.printf("Transforming class %s...%n", className);

            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.get(classNameToFind);

            CtMethod methodToReplaceBody = ctClass.getDeclaredMethod(methodNameToReplaceBody);
            methodToReplaceBody.setBody(String.format(
                    "{ System.out.println(\"Executing replaced body of method %s...\"); }",
                    methodNameToReplaceBody));

            CtMethod methodToInsertBeforeBody = ctClass.getDeclaredMethod(methodNameToInsertBeforeBody);
            methodToInsertBeforeBody.insertBefore(
                    String.format("System.out.println(\"Executing inserted part of method %s...\");",
                            methodNameToInsertBeforeBody));

            byte[] bytes = ctClass.toBytecode();
            ctClass.detach();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
