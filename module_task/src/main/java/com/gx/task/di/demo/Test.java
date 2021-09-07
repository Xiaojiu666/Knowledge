package com.gx.task.di.demo;

public class Test {

    public Clothes get() {
        return newInstance();
    }

    public static Test create() {
        return Test.InstanceHolder.INSTANCE;
    }

    public static Clothes newInstance() {
        return new Clothes();
    }

    private static final class InstanceHolder {
        private static final Test INSTANCE = new Test();
    }
}
