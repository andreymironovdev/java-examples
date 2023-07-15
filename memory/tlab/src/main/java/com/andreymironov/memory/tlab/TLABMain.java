package com.andreymironov.memory.tlab;

public class TLABMain {
    public static void main(String[] args) {
        System.out.println("Starting TLAB Main...");
        int counter = 0;
        for (int i = 0; i < 1000; i++) {
            Object object = new Object();
            counter += object.hashCode();
        }
    }
}
