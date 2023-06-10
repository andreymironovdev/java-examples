package com.andreymironov.instrumentation;

import javassist.*;
import lombok.SneakyThrows;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Optional;

public class CustomClassFileTransformer implements ClassFileTransformer {
    @SneakyThrows({NotFoundException.class, CannotCompileException.class})
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        System.out.println(className);
        if (className.equals(Optional.class.getName())) {
            System.out.println("OK lets go");
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.get(Optional.class.getName());
            CtMethod ifEmpty = CtNewMethod.make(
                    Modifier.PUBLIC,
                    CtClass.voidType, "ifEmpty",
                    new CtClass[]{pool.get(Runnable.class.getName())},
                    null,
                    """
                            if (value == null) {
                                action.accept(value);
                            }
                            """,
                    ctClass);
            ctClass.addMethod(ifEmpty);
        }

        return ClassFileTransformer.super.transform(loader, className, classBeingRedefined, protectionDomain,
                classfileBuffer);
    }
}
