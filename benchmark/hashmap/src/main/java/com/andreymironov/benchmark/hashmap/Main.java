package com.andreymironov.benchmark.hashmap;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final Map<KeyWithConstantHashCode, Integer> SYNCHRONIZED_HASH_MAP = Collections.synchronizedMap(
            new HashMap<>());
    public static final Map<KeyWithConstantHashCode, Integer> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 3)
    @Measurement(iterations = 3)
    @Threads(4)
    public void putSynchronizedMap() {
        for (int i = 0; i < 1000; i++) {
            KeyWithConstantHashCode key = new KeyWithConstantHashCode(i);
            SYNCHRONIZED_HASH_MAP.put(key, i);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 3)
    @Measurement(iterations = 3)
    @Threads(4)
    public void putConcurrentHashMap() {
        for (int i = 0; i < 1000; i++) {
            KeyWithConstantHashCode key = new KeyWithConstantHashCode(i);
            CONCURRENT_HASH_MAP.put(key, i);
        }
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}
