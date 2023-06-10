package com.andreymironov.instrumentation.consumer;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Optional.of("abc");
        System.out.println("Hello!!!");
    }
}
