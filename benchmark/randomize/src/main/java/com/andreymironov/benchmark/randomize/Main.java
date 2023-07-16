package com.andreymironov.benchmark.randomize;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Random RANDOM = new Random();
    private static final ThreadLocalRandom THREAD_LOCAL_RANDOM = ThreadLocalRandom.current();

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 3)
    @Measurement(iterations = 3)
    @Threads(4)
    public int getOldRandom() {
        return RANDOM.nextInt();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 3)
    @Measurement(iterations = 3)
    @Threads(4)
    public int getNewRandom() {
        return THREAD_LOCAL_RANDOM.nextInt();
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}
