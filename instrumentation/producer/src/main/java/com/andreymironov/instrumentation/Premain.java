package com.andreymironov.instrumentation;

import java.lang.instrument.Instrumentation;

public class Premain {
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("Executing javaagent premain method...");
        instrumentation.addTransformer(new MethodsClassFileTransformer());
    }
    public static void agentmain(String args, Instrumentation inst) {
        System.out.println("Executing javaagent premain method...");
    }
}
