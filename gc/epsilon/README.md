# Epsilon Garbage Collector

It was introduced in Java 11 and presents the lowest possible GC overhead.

Epsilon is a no-op garbage collector.

JEP 318 explains that “[Epsilon] … handles memory allocation but does not implement any actual memory reclamation
mechanism. Once the available Java heap is exhausted, the JVM will shut down.”

There are some cases when we know that the available heap will be enough, so we don't want the JVM to use resources to
run GC tasks.

Some examples of such cases (also from the related JEP):

* Performance testing
* Memory pressure testing
* VM interface testing
* Extremely short lived jobs
* Last-drop latency improvements
* Last-drop throughput improvements