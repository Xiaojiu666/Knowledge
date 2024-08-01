
#### == 和 === 区别
== 比较的是值 通过Intrinsics.areEqual(b, c)来比较两个对象的值
=== 比较的时地址值


#### 协变 逆变 不变
协变(out) 只能作为输出,即只能读取类型参数T,不能写入T。适用于只输出泛型类型的场景。

```JAVA
interface Source<out T> {
    fun get(): T 
}
```
逆变(in) 只能作为输入,即只能写入类型参数T,不能读取T。适用于只输入泛型类型的场景。

```JAVA
interface Comparable<in T> {
    fun compareTo(other: T): Int
}
```
不变(默认)既可以作为输入也可以作为输出。

协变和逆变性通过类型投影来实现,可以帮助我们创建更灵活安全的泛型API。
注:
-   类不能申明出协变或逆变  
-   函数参数不能是逆变的,返回值不能是协变的  
-   出协变(out)时要确保泛型类型参数T只出现在输出位置  
-   逆变(in)时要确保T只出现在输入位置  

#### 为什么协变只能读取泛型，不能修改泛型？
答：因为 例如<Object> = <String> 泛型接收端是Object，而泛型具体端是String，由于具体端有很多很多Object的子类，
而泛型会被泛型擦除，所以无法明确你到底要修改那个子类啊
为什么逆变只能修改泛型，不能读取泛型？
答：因为 例如<String> = <Object> 泛型接收端是String，而泛型具体端是Object，由于接收端是String，而读取时，
会读取到String的父类，但是接收端是String，你却读取到String的父类，这个本来就是不合理的

#### object作用
- 创建单例对象(饿汉式)
    使用 object 关键字可以创建一个单例对象，该对象只能有一个实例，并且在整个应用程序生命周期中都可以被访问。这种方式是实现单例模式的一种简单且安全的方式。
- 定义匿名对象
    ```
    fun printInfo() {
        val person = object {
            val name = "John"
            val age = 30
        }
        println("${person.name}, ${person.age} years old")
    }
    ```
- 实现对象表达式
Kotlin 中的对象表达式允许你在需要时创建一个特定类型的临时对象，并实现其方法或属性。使用 object 关键字可以方便地创建对象表达式。
    ```
        val runnable = object : Runnable {
            override fun run() {
                println("Running...")
            }
        }
    ```
-  定义伴生对象
    ```
    class MyClass {
    companion object {
        const val CONST = 1
    }  
    }
    ```



#### 常见高阶函数

#### 高阶函数和回调函数的区别
-   高阶函数：
高阶函数是指接受函数作为参数，或者返回函数作为结果的函数。
高阶函数可以让代码更加灵活，可以将函数作为参数传递给其他函数，从而实现不同的行为。
Kotlin 中的高阶函数可以使用 lambda 表达式来实现，这样可以简化代码并提高可读性。
-   回调函数：
回调函数是指将一个函数作为参数传递给另一个函数，并在特定的事件发生时由另一个函数调用。
回调函数通常用于异步编程中，比如处理异步操作的结果或者处理事件驱动的编程模型。

#### 内联函数
不带参数，或是带有普通参数的函数，不建议使用 inline  
带有 lambda 函数参数的函数，建议使用 inline  
它便是在编译过程中，因为 lambda 参数 多出来的类，无疑中会增加内存的分配。

#### noinline 
内联函数的「函数参数」 不允许作为参数传递给非内联的函数，
基础级别


#### 什么是Kotlin？

Kotlin是由JetBrains开发的一种静态类型编程语言。它与Java完全兼容，主要用于Android开发。
Kotlin的主要特性是什么？

简洁的语法
安全（空安全，类型推断）
与Java互操作
工具友好（可与所有现有的Java框架和库一起使用）
表达力强（提供许多强大的功能，如高阶函数、lambda等）
解释Kotlin中的空安全。

Kotlin旨在消除代码中的NullPointerException。Kotlin中的类型默认是不可为空的。要允许变量持有空值，需要显式地用?标记类型。
#### val和var的区别是什么？

val（值）是不可变的，只读的。一旦分配，它不能被重新分配。
var（变量）是可变的，可以重新分配。
如何在Kotlin中创建数据类？

kotlin
复制代码
data class User(val name: String, val age: Int)
中级水平
什么是Kotlin中的伴生对象？

伴生对象是与类关联的单例对象。它允许你定义与类而不是类的实例相关的方法和属性。
解释Kotlin中by关键字的用法。

by关键字用于委托。例如，一个类可以将特定接口的实现委托给另一个类。
什么是Kotlin中的扩展函数？

扩展函数允许你向现有类添加功能，而无需修改它们的代码。它们在类的外部定义，但可以像类的方法一样调用。
kotlin
复制代码
fun String.removeFirstLastChar(): String = this.substring(1, this.length - 1)
在Kotlin中如何处理异常？

与Java类似，可以使用try，catch和finally块。
kotlin
复制代码
try {
    // 可能抛出异常的代码
} catch (e: Exception) {
    // 处理异常
} finally {
    // 始终执行的代码
}
==和===在Kotlin中的区别是什么？

==检查结构相等（相当于Java中的equals()）。
===检查引用相等（是否指向同一个对象）。
高级水平
什么是Kotlin中的协程？

协程是Kotlin中用于异步编程的特性。它们是轻量级的线程，允许你以顺序的方式编写异步代码。
如何在Kotlin中启动协程？

kotlin
复制代码
GlobalScope.launch {
    // 协程代码
}
解释协程中的launch和async的区别。

launch用于启动不返回结果的协程。
async用于启动返回Deferred结果的协程，可以被等待。
Kotlin中suspend修饰符的作用是什么？

suspend修饰符用于标记一个可以挂起的函数，意味着它可以暂停并在稍后恢复。
解释高阶函数并举例。

高阶函数是将其他函数作为参数或返回函数的函数。
kotlin
复制代码
fun <T> List<T>.customFilter(predicate: (T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    for (item in this) {
        if (predicate(item)) {
            result.add(item)
        }
    }
    return result
}
如何在Kotlin中确保线程安全？

使用同步块、原子变量或使用适当的调度器进行高层次的并发框架，如协程。
什么是Kotlin中的内联函数及其用途？

内联函数是指在调用点扩展的函数，从而减少函数调用的开销。
kotlin
复制代码
inline fun <T> lock(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    } finally {
        lock.unlock()
    }
}
如何在Kotlin中定义密封类，及其使用场景？

密封类用于表示受限的类层次结构，其中一个值只能是有限集合中的一种类型。
kotlin
复制代码
sealed class Shape
class Circle(val radius: Double) : Shape()
class Rectangle(val height: Double, val width: Double) : Shape()
解释Kotlin中的具体化类型参数的概念。

具体化类型参数允许你在运行时访问类型信息，这在泛型中通常是被擦除的。
kotlin
复制代码
inline fun <reified T> Gson.fromJson(json: String): T = this.fromJson(json, T::class.java)
Kotlin集合中的map和flatMap有什么区别？

map将集合中的每个元素转换为新元素并返回转换后的元素列表。
flatMap将集合中的每个元素转换为一个可迭代的元素，并将结果展平成一个单一列表。