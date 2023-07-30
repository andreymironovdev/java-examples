package com.andreymironov.instrumentation.consumer;

public class ClassToInstrument {
    public static void methodToInstrument() {
        System.out.printf("Executing method %s...%n", ReflectionUtils.getCurrentMethodName());
    }
}
