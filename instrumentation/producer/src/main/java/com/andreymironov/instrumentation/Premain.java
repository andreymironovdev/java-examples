package com.andreymironov.instrumentation;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Optional;

public class Premain {
    public static void premain(String args, Instrumentation instrumentation) throws UnmodifiableClassException {
        System.out.println("Starting premain...");
        instrumentation.addTransformer(new CustomClassFileTransformer());
        instrumentation.retransformClasses(Optional.class);
    }
    public static void agentmain(String args, Instrumentation inst) {
        System.out.println("Starting agentmain...");
    }
}
