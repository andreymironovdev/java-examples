# G1 Garbage Collector

The G1 (garbage-first) garbage collector came with Java 7 (minor version 4) and is an upgrade of the CMS GC. It combines
different algorithms in a clever way. The G1 collector is parallel, concurrent, and aims for short pauses of the
application. It employs a technique called incrementally compacting.
The G1 garbage collector divides the heap into smaller regions: much smaller than the generational garbage collector. It
works with these smaller memory segments to mark and sweep them. It keeps track of the amount of reachable and
unreachable objects per memory region. The regions with the most unreachable objects are garbage collected first since
that frees up the most memory. That’s why it is called the garbage-first garbage collector. Regions with the most
garbage are collected first.
It does all this while copying objects from one region to another region. This will result in freeing up the first
region completely. This way the G1 GC kills two birds with one stone: achieving GC and compacting at the same time. This
is why it is such an upgrade compared to CMS collector.
The G1 GC is a great garbage collector. You may wonder whether this garbage collector manages to work without
stop-the-world. No, the compacting still needs to happen this way. But due to the smaller regions, the pauses are much
shorter.
Another new feature of the G1 GC garbage collector is string deduplication. This is literally what you’d think it is:
the garbage collector runs a process to inspect the String objects. When it finds String objects that contain the same
content but refer to different char arrays on the heap, they will be updated to both point to the same char array. This
makes the other char array eligible for GC and this way, the memory usage is optimized. As if this wasn’t exciting
enough, it happens completely concurrently! This option will need to be enabled using the following command: -XX:
+UseStringDeduplication.
Just like the CMS GC, the G1 GC tries to do a lot of the GC concurrently. So, the application threads don’t need to be
paused most of the time. However, if the G1 GC cannot free up enough memory and the application is allocating more than
can be freed up concurrently, the application threads need to be paused.
The G1 garbage collector is the go-to GC for powerful systems that are high performing and have a large memory space. 