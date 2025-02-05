
## 1. 基础知识

### 1.1 什么是 Kotlin？它与 Java 的主要区别是什么？
Kotlin 是一种现代化的静态类型编程语言，官方推荐用于 Android 开发。
**区别**：
- Kotlin 语法简洁，减少样板代码。
- Kotlin 支持空安全（Null Safety）。
- Kotlin 具有扩展函数和更好的函数式编程支持。
- Kotlin 具有协程支持，适用于异步编程。

### 1.2 Kotlin 的基本数据类型有哪些？
- 数字类型（Byte 1字节、Short 2字节、Int 4字节、Long 8字节、Float 4字节、Double 8字节）
- 字符类型（Char 2字节）
- 布尔类型（Boolean 1 字节）
- 字符串类型（String）
- 数组（Array）

### 1.3 Kotlin 的可空类型（Nullable Types）如何处理？
使用 `?` 声明可空类型，例如 `var name: String? = null`，并使用 `?.`（安全调用）或 `!!`（非空断言）处理。

### 1.4 Kotlin 的 `val` 和 `var` 有什么区别？
- `val`：不可变变量（类似 Java 的 `final`）。
- `var`：可变变量。

### 1.5 什么是扩展函数（Extension Function）？
扩展函数允许在不修改类的情况下扩展其功能。例如：
```kotlin
fun String.reverse(): String {
    return this.reversed()
}
```

### 1.6 Kotlin 的 `when` 语句如何使用？
`when` 是 Kotlin 版的 `switch`，支持复杂条件判断：
```kotlin
when (value) {
    1 -> println("One")
    in 2..5 -> println("Between 2 and 5")
    else -> println("Other")
}
```

### 1.7 Kotlin 的 `object` 关键字有哪些用法？
- **对象声明**（单例模式）。
- **伴生对象**（类似 Java 的静态成员）。
- **匿名对象**（用于临时创建对象）。

### 1.8 Kotlin 的 `sealed class` 作用是什么？
`sealed class` 适用于定义受限的继承结构，比如 UI 状态管理。

### 1.9 什么是数据类（Data Class）？
数据类用于存储数据，自动生成 `toString()`、`equals()`、`hashCode()` 等方法。
```kotlin
data class User(val name: String, val age: Int)
```

### 1.10 `lateinit` 和 `lazy` 的区别是什么？
- `lateinit` 适用于可变变量（`var`），在初始化前不能访问。
- `lazy` 适用于不可变变量（`val`），只会在第一次使用时初始化。

## 2. Kotlin 高级特性

### 2.1 什么是协程（Coroutines）？它的优点是什么？
协程是一种轻量级线程，支持挂起和恢复，避免阻塞主线程，适用于异步编程。

### 2.2 `suspend` 关键字的作用？
`suspend` 用于标记挂起函数，可在协程内调用。

### 2.3 `launch` 和 `async` 的区别？
- `launch` 启动协程但不会返回结果。
- `async` 启动协程并返回 `Deferred`，可通过 `await()` 获取结果。

### 2.4 `withContext` 的作用是什么？
用于切换协程上下文，如 `withContext(Dispatchers.IO)` 切换到 IO 线程。

### 2.5 什么是 `Flow`？与 `LiveData` 的区别是什么？
- `Flow` 是 Kotlin 协程提供的数据流，支持背压。
- `LiveData` 主要用于 Android UI 组件，生命周期感知但不支持挂起函数。

### 2.6 Kotlin 中的 `inline` 关键字的作用？
`inline` 用于内联函数，减少 lambda 开销。

### 2.7 `crossinline` 和 `noinline` 有什么区别？
- `crossinline` 防止 lambda 被非局部返回。
- `noinline` 禁止 lambda 内联。

### 2.8 什么是 `reified` 关键字？
在泛型函数中用于保留泛型类型信息，允许在运行时获取类型。

## 3. Android 相关

### 3.1 在 Android 开发中，如何使用 Kotlin 协程处理网络请求？
使用 `Retrofit`+`Coroutine`：
```kotlin
suspend fun getData(): Response<Data> {
    return apiService.getData()
}
```

### 3.2 ViewModel 如何使用协程？
在 `viewModelScope` 中启动协程：
```kotlin
viewModelScope.launch {
    val data = repository.getData()
}
```

### 3.3 `LiveData` 和 `StateFlow` 的区别？
- `LiveData` 适用于 UI 层，生命周期感知。
- `StateFlow` 适用于数据流，支持挂起。

### 3.4 Kotlin 如何实现单例模式？
使用 `object` 关键字：
```kotlin
object Singleton {
    val instance = "Hello"
}
```

## 4. 性能优化与最佳实践

### 4.1 如何优化 Kotlin 代码性能？
- 使用 `inline` 减少 lambda 开销。
- 避免 `vararg` 造成的数组分配。
- 使用 `lazy` 延迟初始化。

### 4.2 Kotlin 协程的最佳实践是什么？
- 适当使用 `Dispatchers` 控制线程。
- 在 `viewModelScope` 中管理生命周期。
- 使用 `try-catch` 处理异常。

### 4.3 如何避免 Kotlin 代码中的内存泄漏？
- 使用 `weak reference` 避免 Activity 持有 ViewModel 引用。
- 在 `onDestroy` 取消协程。

### 4.4 在 Android 项目中，如何使用 Kotlin 编写高效的 RecyclerView 适配器？
使用 `ListAdapter` 和 `DiffUtil` 优化性能。

### 4.5 如何使用 Kotlin 提高 Android 项目的可读性和可维护性？
- 使用扩展函数减少重复代码。
- 适当使用 `sealed class` 处理 UI 状态。
- 使用 `data class` 处理数据对象。

---




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