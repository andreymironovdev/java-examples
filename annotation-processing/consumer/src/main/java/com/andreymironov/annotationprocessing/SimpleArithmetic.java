package com.andreymironov.annotationprocessing;

@Arithmetic
public interface SimpleArithmetic {
    int add2MultiplyBy3(int value);

    int divideBy5Subtract4Negate(int value);
}
