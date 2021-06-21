package com.sn.accountbooks.test

class TestCode{

    val items = listOf("apple", "banana", "kiwifruit")

    fun sum(a:Int,b:Int ) = a+b

    fun getStringLength(obj: Any): Int? {
        if (obj is String) {
            // `obj` 在该条件分支内自动转换成 `String`
            return obj.length
        }

        // 在离开类型检测分支后，`obj` 仍然是 `Any` 类型
        return null
    }


    fun forFun(){
        for (item in items) {
            println(item)
        }
    }

    fun filterList(){
        val positives = items.filter { it.equals("apple")  }
        println(positives)
    }

    fun getString():String{
        return "AAAA"
    }

    val lazyValue: String by lazy {
        getString()
        "Hello"
    }
}