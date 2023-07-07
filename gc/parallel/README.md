# Parallel Garbage Collector

The parallel garbage collector is the default garbage collector of Java 8. It uses the mark-and-copy approach for the
young generation and the mark-sweep-compact approach for the old generation, just like the serial garbage collector.
However, and this might come as a surprise, it does so in parallel. In this case, parallel means that uses multiple
threads to clean up the heap space. So, there is not one single thread taking care of the marking, copying, and
compacting phases, but multiple threads. Even though it is still stop-the-world, it performs better than the serial
garbage collector, since the world needs to be stopped for a shorter amount of time.
The parallel garbage collector will work well on machines with multiple cores. On (rarer) single-core machines, the
serial garbage collector is probably a better choice, due to the costs of managing multiple threads and not really
parallel processing on the single core.