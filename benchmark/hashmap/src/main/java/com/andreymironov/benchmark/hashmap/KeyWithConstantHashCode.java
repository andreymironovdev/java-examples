package com.andreymironov.benchmark.hashmap;

public class KeyWithConstantHashCode {
    int value;

    public KeyWithConstantHashCode(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyWithConstantHashCode that = (KeyWithConstantHashCode) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
