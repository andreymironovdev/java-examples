package com.andreymironov.instrumentation;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Agentmain {
    public static void agentmain(String args, Instrumentation instrumentation) {
        System.out.println("Executing java agent agentmain method...");
        String[] argsSplitted = args.split(",");
        String classNameToInstrument = argsSplitted[0];
        String methodNameToInstrument = argsSplitted[1];

        try {
            instrumentation.addTransformer(new JavassistMethodTransformer(classNameToInstrument, methodNameToInstrument), true);
            instrumentation.retransformClasses(Class.forName(classNameToInstrument));
        } catch (UnmodifiableClassException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
