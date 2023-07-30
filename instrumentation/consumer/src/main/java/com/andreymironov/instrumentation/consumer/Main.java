package com.andreymironov.instrumentation.consumer;

public class Main {
    public static void methodToInsertBeforeBody() {
        System.out.println("Executing methodToInsertBeforeBody method...");
    }

    public static void methodToReplaceBody() {
        System.out.println("Executing methodToReplaceBody method...");
    }

    public static void main(String[] args) {
        System.out.println("Executing main method...");
        methodToInsertBeforeBody();
        methodToReplaceBody();
    }
}
