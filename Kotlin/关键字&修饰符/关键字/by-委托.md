###  委托(代理)
委托，也就是委托模式，它是23种经典设计模式种的一种，又名代理模式，在委托模式中，两个对象参与处理同一请求，接受请求的对象讲请求委托给另外一个对象来处理。Kotlin直接支持委托模式，更加优雅，简洁。kotlin通过关键字by实现委托。  
#####  委托模式中角色
有三个角色，约束、委托对象和被委托对象。
- 约束： 约束是接口或者抽象类，它定义了通用的业务类型，也就是需要被代理的业务
- 被委托对象： 具体的业务逻辑执行者
- 委托对象： 负责对真是角色的应用，将约束累定义的业务委托给具体的委托对象。


##### 委托机制作用
1. 实现代码复用
-   属性委托：能够把属性的管理逻辑提取出来并复用。例如，lazy 委托可实现属性的懒加载，在首次访问属性时才进行初始化操作，避免不必要的计算或资源消耗。在多个属性都需要懒加载时，直接使用   -lazy 委托即可，无需为每个属性重复编写懒加载逻辑。
-   类委托：允许一个类将接口的实现委托给另一个对象，避免了继承可能带来的代码冗余。当多个类需要实现相同接口的部分功能时，可将这些功能封装在一个委托类中，其他类通过委托复用这些功能。

2. 分离关注点
- 属性委托：可以把属性的访问控制、初始化逻辑等和类的核心业务逻辑分离开来。例如，一个类有多个属性需要进行非空检查，可创建一个专门的委托类处理非空检查逻辑，让类的主体代码更专注于业务功能。
- 类委托：：使得类的功能更加模块化，每个类或对象只负责自己擅长的部分。一个类可能需要实现多个接口，将不同接口的实现委托给不同的对象，每个对象专注于实现特定的功能，提高代码的可维护性。

3. 增强代码灵活性
- 属性委托：可以在运行时动态改变属性的行为。通过更换委托对象，就能改变属性的获取和设置逻辑，而无需修改类的结构。
- 类委托：在不修改类继承关系的前提下，为类添加或修改功能。当需要为一个类添加新功能时，可创建一个新的委托类实现该功能，然后将委托对象替换为新的委托类实例。

4. 遵循设计原则
- 属性委托：符合单一职责原则，每个委托类只负责一个属性的特定管理逻辑。这样，当属性的管理逻辑发生变化时，只需修改对应的委托类，不会影响到其他部分的代码。
- 类委托：遵循组合优于继承原则。使用委托可以避免因继承带来的耦合问题，通过组合不同的委托对象，可灵活地组装类的功能。

### 类委托
假设，有一份工作，本应该由`Leader`开发,但是`Leader` 过于忙碌，将这份工作 委托给了`Worker`
```Java
// 约束类
interface Person {
    //工作
    fun work();
}

//委托对象
class Leader(val woker: Person): Person by woker {
}

// 被委托对象
class Worker(val name: String) : Person {
    override fun work() {
        print("$name Write PPT ~ ")
    }
}
//测试
fun main() {
    val worker = Worker("小明")
    Leader(worker).work()
}

// 输出 : 小明 Write PPT ~
```

总结: 2个对象参与处理同一请求，这个请求就是我们约束类的逻辑，因此委托类和被委托类都需要实现我们的约束接口。

### 属性委托
在Kotlin 中，有一些常见的属性类型，虽然我们可以在每次需要的时候手动实现它们，但是很麻烦，各种样板代码存在，我们知道，Kotlin可是宣称要实现零样板代码的。为了解决这些问题呢？Kotlin标准为我们提供了委托属性。  

基础语法  
```java
val/var <属性名>: <类型> by <表达式>

```
######  属性委托的原理

前面讲的委托中，我们有个约束角色，里面定义了代理的业务逻辑。而委托属性呢？其实就是上面的简化，被代理的逻辑就是这个属性的get/set方法。get/set会委托给被委托对象的setValue/getValue方法，因此被委托类需要提供setValue/getValue这两个方法。如果是val 属性，只需提供getValue。如果是var 属性，则setValue/getValue都需要提供。

```Java
//  委托的属性
var workName :String by WorkNameDelegate()

//  被委托属性的Set/Get实现
class WorkNameDelegate {

    private var value : String = "Worker"

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef ，他的 默认 ${property.name} 是 $value !"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this.value = value
        println("$thisRef ，他的 ${property.name} 被设置为 $value !")
    }

}
//测试
fun main() {
    val worker = Worker()
    println( worker.workName)
    worker.workName = "Android Developer Manager"
    println( worker.workName)
}
//输出
com.gx.kt.study.by.cls.Worker@50f8360d ，他的 默认 workName 是 Worker !
com.gx.kt.study.by.cls.Worker@50f8360d ，他的 workName 被设置为 Android Developer Manager !
com.gx.kt.study.by.cls.Worker@50f8360d ，他的 默认 workName 是 Android Developer Manager !
```
其中的参数解释如下：  
  - thisRef —— 必须与 属性所有者 类型（对于扩展属性——指被扩展的类型）相同或者是它的超类型；
  - property —— 必须是类型 KProperty<*>或其超类型。(类似于java 中的Field)
  - value —— 必须与属性同类型或者是它的子类型。  


###### 另一种自定义属性委托的方式    

要实现属性委托，就必须要提供getValue/setValue方法，对于比较懒的同学可能就要说了，这么复杂的参数，还要每次都要手写，真是麻烦，一不小心就写错了。确实是这样，为了解决这个问题， Kotlin 标准库中声明了2个含所需 operator方法的 ReadOnlyProperty / ReadWriteProperty 接口。
```Java
interface ReadOnlyProperty<in R, out T> {
    operator fun getValue(thisRef: R, property: KProperty<*>): T
}

interface ReadWriteProperty<in R, T> {
    operator fun getValue(thisRef: R, property: KProperty<*>): T
    operator fun setValue(thisRef: R, property: KProperty<*>, value: T)
}
```
被委托类 实现这两个接口其中之一就可以了，val 属性实现ReadOnlyProperty，var属性实现ReadOnlyProperty。 

### Kotlin 标准库中提供几个委托
Kotlin 标准库中提供了几种委托，例如：
- 延迟属性（lazy properties）: 其值只在首次访问时计算；
- 可观察属性（observable properties）: 监听器会收到有关此属性变更的通知；
- 把多个属性储存在一个映射（map）中，而不是每个存在单独的字段中。

###### 延迟属性 by lazy
lazy() 是接受一个 lambda 并返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托： 第一次调用 get() 会执行已传递给 lazy() 的 lambda 表达式并记录结果， 后续调用 get() 只是返回记录的结果。
```Java
val age : Int by lazy {
    var realAge :Int = 10
    realAge = 10 + 1
    println("计算年龄 $realAge ")
    realAge
}

fun main() {
    println(age)
    println(age)
    println(age)
    println(age)
}
// 计算年龄 11
// 11
// 11
// 11
// 11
```
 lazy() 方法 也可以接受参数 ，默认为LazyThreadSafetyMode.SYNCHRONIZED  
 -  LazyThreadSafetyMode.SYNCHRONIZED: 添加同步锁，使lazy延迟初始化线程安全
 -  LazyThreadSafetyMode. PUBLICATION： 初始化的lambda表达式可以在同一时间被多次调用，但是只有第一个返回的值作为初始化的值。
 -  LazyThreadSafetyMode. NONE：没有同步锁，多线程访问时候，初始化的值是未知的，非线程安全，一般情况下，不推荐使用这种方式，除非你能保证初始化和属性始终在同一个线程

###### 可观察属性 observable / vetoable
如果你要观察一个属性的变化过程，那么可以将属性委托给Delegates.observable, observable函数原型如下：
```Java
var number: Int by Delegates.observable(1){
        property, oldValue, newValue ->
       println(" 属性名  : ${property.name}: 从  $oldValue -> $newValue ")
}
fun main() {
      number = 2;
      number = 3;
}
// 属性名  : number: 从  1 -> 2
// 属性名  : number: 从  2 -> 3
```
相比observable，vetoable 可以控制 新值的配置是否生效。假设一个人得年龄上限最大为100。
```Java
var age: Int by Delegates.vetoable(0) {
    property, oldValue, newValue ->
    newValue <= 100 //最后一行必须是逻辑
}

fun main() {
    println(age)
    age = 2;
    println(age)
    age = 101;
    println(age)
}
// 0
// 2
// 2

```
可以看出，当年龄大于 100时，并未赋值成功


###### 委托给另一个属性
一个属性可以把它的 getter 与 setter 委托给另一个属性。
- 顶层属性
  var newValue: Int by ::value
- 同一个类的成员或扩展属性  
  var newValue: Int by this::newValue
- 另一个类的成员或扩展属性  
  val delegatedToAnotherClass: Int by anotherClassInstance::anotherClassInt

      委托属性时，查到的结果始终是最新值



###### 属性存储在映射中
还有一种情况，在一个映射（map）里存储属性的值，使用映射实例自身作为委托来实现委托属性，如：
```Java
fun main() {
    val user = User(mapOf(
        "name" to "guoxu",
        "age"  to 28
    ))
    println("name=${user.name} age=${user.age}")
}

class User(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
}
```
`注意:map的 key 和 属性名, value 和属性的数据格式 必须对应，否则报错`




### 总结
像Android 日常开发中，就有很多需要初始化一次，后续无须初始化的地方，例如:findViewById，SP存储等，我们就可以使用 `by lazy()`


### 参考资料
- [一文彻底搞懂Kotlin中的委托](https://juejin.cn/post/6844904038589267982)
- [kotlin中文文档](https://book.kotlincn.net/text/delegation.html)
