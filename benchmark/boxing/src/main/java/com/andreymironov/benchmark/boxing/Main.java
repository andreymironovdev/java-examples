package com.andreymironov.benchmark.randomize;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 3)
    @Measurement(iterations = 3)
    @Threads(4)
    public long getWithoutBoxing() {
        long sum = 0L;
        for (long i = 0; i < 1_000; i++) {
            sum += i;
        }
        return sum;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 3)
    @Measurement(iterations = 3)
    @Threads(4)
    public long getWithBoxing() {
        Long sum = 0L;
        for (long i = 0; i < 1_000; i++) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}
