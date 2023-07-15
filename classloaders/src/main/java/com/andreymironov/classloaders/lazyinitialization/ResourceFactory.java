package com.andreymironov.classloaders.lazyinitialization;

public class ResourceFactory {
    static {
        System.out.println("ResourceFactory initialized");
    }

    private static class ResourceHolder {
        static {
            System.out.println("ResourceHolder initialized");
        }
        public static final Object RESOURCE = new Object();
    }

    public static Object getResource() {
        return ResourceHolder.RESOURCE;
    }
}
