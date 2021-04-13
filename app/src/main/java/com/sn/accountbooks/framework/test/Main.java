package com.sn.accountbooks.framework.test;

import android.util.Log;

public class Main {
    public static void main(String[] args) {
        String a = "2147483647";
//        float z = 1.000000000000000;
        int i = Integer.parseInt(a);
        System.out.println(i);
    }

    public static void test(String event, String desc) {
        String a = "58000000000000000000000000000000";
        double b = 100.0;
        float c = 100.0f;
        Integer d = 0;
        if (event.equals("ABC")) {
            String num1 = desc.split(event)[0];
            String num2 = desc.split(event)[0];
            Integer e = Integer.parseInt(a) + Integer.parseInt(num2);
        }
//        return e;

    }
}
