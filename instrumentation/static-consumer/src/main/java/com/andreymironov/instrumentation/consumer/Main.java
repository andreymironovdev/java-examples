package com.andreymironov.instrumentation.consumer;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Executing method %s...%n", ReflectionUtils.getCurrentMethodName());
        ClassToInstrument.methodToInstrument();
    }
}
