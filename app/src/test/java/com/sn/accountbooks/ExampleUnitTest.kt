package com.sn.accountbooks

import android.util.Log
import com.sn.accountbooks.test.Person
import com.sn.accountbooks.test.TestCode
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val TAG = "ExampleUnitTest"

    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
        val testCode = TestCode()
        val sum = testCode.sum(1, 2)
        System.out.println("sum $sum")
        testCode.filterList()
        testCode.forFun()
        println(testCode.lazyValue)
        println(testCode.lazyValue)
        val person = Person("a", "b")
        System.out.println("person ${person.toString()}")

    }
}