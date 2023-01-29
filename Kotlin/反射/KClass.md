##  Class

### KClass

```Java
fun testKotlin(){
    println(T::x) // property x
    println(T::class.java) // 打印：class com.gx.accountbooks.HomeActivity$T
    println(T(10).javaClass) //class com.gx.accountbooks.HomeActivity$T
    println(T(10).javaClass.kotlin) //class com.gx.accountbooks.HomeActivity$T (Kotlin reflection is not available)
    println(String::class.java) // class java.lang.String
    println(String.javaClass) //class kotlin.jvm.internal.StringCompanionObject
}

class T(val x:Int)
```

.javaClass



### 参考资料
- [Android Kotlin的Class、反射、泛型](https://www.jianshu.com/p/a900ee71ae7f)
