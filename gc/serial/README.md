# Serial Garbage Collector

The serial GC runs on a single thread and uses the stop-the-world strategy. This means that the application is not
running its main tasks when the garbage collector runs. It is the simplest option for garbage collection.
For the young generation, it uses the mark strategy to identify which objects are eligible for GC and the sweep with
copying approach for the actual freeing of the memory. For the old generation, it uses the mark and sweep with
compacting approach.
