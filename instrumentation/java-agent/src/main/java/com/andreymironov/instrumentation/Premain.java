package com.andreymironov.instrumentation;

import java.lang.instrument.Instrumentation;

public class Premain {
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("Executing java agent premain method...");
        String classNameToInstrument = System.getProperty("classNameToInstrument");
        String methodNameToInstrument = System.getProperty("methodNameToInstrument");
        instrumentation.addTransformer(new JavassistMethodTransformer(classNameToInstrument, methodNameToInstrument));
    }
}
