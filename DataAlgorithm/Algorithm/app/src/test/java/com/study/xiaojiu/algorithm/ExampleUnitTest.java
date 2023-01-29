package com.study.xiaojiu.algorithm;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        //   System.out.println(SimpleAlgorithm.isHappyNumber(4));
        //System.out.println(SimpleAlgorithm.flipWord("AA bb cc dd"));
        // System.out.println(SimpleAlgorithm.aplusb(6, 1));
        System.out.println(SimpleAlgorithm.digitCounts(9, 999));
    }
    @Test
    public void test_hasSet() {
        HashSet<String> objects = new HashSet<>();
        objects.add("AA");
        objects.add("AA");
        for (String object :
                objects) {
            System.out.println(object);
        }
    }
}