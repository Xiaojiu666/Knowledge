package com.sn.desnginpatterns.Single;

public class StaticInnerSingle {

    public StaticInnerSingle getInstance() {
        return StaticInnerSingleHolder.mStaticInnerSingle;
    }

    private static class StaticInnerSingleHolder {
        public static final StaticInnerSingle mStaticInnerSingle = new StaticInnerSingle();
    }
}
