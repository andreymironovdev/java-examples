# Z Garbage Collector

Java 15 gave us yet another production-ready implementation of the garbage collector, the Z garbage collector (ZGC). It
does all the garbage collecting concurrently and does not need to pause the application for more than 10 ms per pause.
It manages to do this by starting with marking the live objects. It doesn’t keep a map but uses reference coloring.
Reference coloring means that the live state of reference is stored as the bits that are part of the reference. This
requires some extra bits, which is why the ZGC only works on 64-bit systems and not on 32-bit systems.
Fragmentation is avoided by using relocation. This process happens in parallel with the application in order to avoid
pauses of more than 10 ms, but this happens while the application is being executed.
Without extra measurements, this could lead to unpleasant surprises. Imagine that we were trying to access a certain
object with the reference, but while doing so, it got relocated and has a new reference. The old memory location could
be overwritten or cleared already. In such a scenario, debugging would be a nightmare.
Of course, the Java team would not push a garbage collector to production with issues like that. They introduced load
barriers to deal with this. The load barriers run whenever a reference from the heap is loaded. It checks the metadata
bits of the reference and, based on the result, it may or may not do some processing before retrieving the result. This
magic is called remapping.