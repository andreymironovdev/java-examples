package com.andreymironov.common;

// TODO: add doc
public class DocumentedClass {
    /**
     * @throws RuntimeException if flag is true
     */
    public void throwException(boolean flag) {
        if (flag) {
            throw new RuntimeException("Oops");
        }
    }
}
