package com.andreymironov.common;

import java.util.ArrayList;
import java.util.List;

public class DiedReferenceImpl extends DiedReference {
    public static List<DiedReference> diedReferences = new ArrayList<>();

    @Override
    protected void finalize() throws Throwable {
        diedReferences.add(this);
    }
}
