package com.andreymironov.concurrency.doublecheckedlocking;

public class EscapingReferenceDCLResourceFactory {
    private EscapingReferenceResource escapingReferenceResource;

    public EscapingReferenceResource getResource() {
        if (escapingReferenceResource == null) {
            synchronized (this) {
                if (escapingReferenceResource == null) {
                    escapingReferenceResource = new EscapingReferenceResource(this, 1, "name");
                }
            }
        }

        return escapingReferenceResource;
    }

    public void setResource(EscapingReferenceResource escapingReferenceResource) {
        this.escapingReferenceResource = escapingReferenceResource;
    }
}
