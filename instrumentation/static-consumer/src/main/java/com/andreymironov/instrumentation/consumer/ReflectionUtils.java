package com.andreymironov.instrumentation.consumer;

public class ReflectionUtils {
    public static String getCurrentMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length >= 2) {
            // Index 0 is getStackTrace(), index 1 is getCurrentMethodName(), so index 2 is the caller method.
            StackTraceElement caller = stackTrace[2];
            return caller.getMethodName();
        }

        // If the method name cannot be determined.
        return null;
    }
}
