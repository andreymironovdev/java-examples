# CMS Garbage Collector

## As of Java 9, it is deprecated.

## As of Java 14, it is removed.

The Concurrent Mark Sweep Garbage Collector (CMS GC) has an improved mark-and-sweep algorithm. It manages to do this
with multiple threads and reduces the pause time drastically. This is the main difference between CMS GC and parallel
garbage collector.
Not all systems can handle sharing resources between the main application and the garbage collector, though, but if they
can, it is a great upgrade in terms of performance compared to the parallel garbage collector.
The CMS GC is a generational garbage collector as well. It has separate cycles for the young and old generation. For the
young generation, it uses mark and copy with stop-the-world. So, during the GC of the young generation, the main
application threads are paused.
The old generation is garbage collected with mostly concurrent mark and sweep. The term mostly concurrent means that it
does most of the GC concurrently, but it will still use stop-the-world twice in a GC cycle. It pauses all the main
application threads for the first time at the very beginning, then during the marking for a very short time, and then
for a (usually) somewhat longer time around the middle of the GC cycle to do the final marking.
These pauses are typically very short, because the CMS GC attempts to collect enough of the old generation while running
concurrently to the main application threads and, this way, prevent it from getting full. Sometimes, this is not
possible. If the CMS GC cannot free up enough while the old generation is getting full, or the application fails to
allocate an object, the CMS GC pauses all the application threads and the main focus shifts to GC. The situation in
which this garbage collector fails to do the GC mostly concurrently is called concurrent mode failure.
If the collector then still cannot free up enough memory, OutOfMemoryError gets thrown. This happens when 98% of the
application time is spent on GC and less than 2% of the heap is recovered.