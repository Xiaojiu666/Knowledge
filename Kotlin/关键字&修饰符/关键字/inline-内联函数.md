### 内联函数
使用高阶函数会带来一些运行时的效率损失：每一个函数都是一个对象，并且会捕获一个闭包。 闭包那些在函数体内会访问到的变量的作用域。 内存分配（对于函数对象和类）和虚拟调用会引入运行时间开销。
简单来说：
当一个函数被内联 inline 标注后，在调用它的地方，会把这个函数方法体中的所以代码移动到调用的地方，而不是通过方法间压栈进栈的方式。

###### 内联inline
通过上面我们知道，内联是对高阶函数再内存上的空间优化。
如果对高阶函数不了解的同学，可以先看官方的介绍，就不做过多介绍了。
我们对class文件做下对比。
```Java
fun main() {
    printFunTime{
        println("This is a fun")
    }
}

fun printFunTime(funTime: () -> Unit) {
    val startTime = System.currentTimeMillis()
    funTime()
    println("Total Time ${System.currentTimeMillis() - startTime}")
}
```
###### 不使用内联inline .class
```Java
// $FF: synthetic method
public static void main(String[] var0) {
   main();
}
//调用printFunTime 传递一个Function0的 实例，
public static final void main() {
   printFunTime((Function0)null.INSTANCE);
}

public static final void printFunTime(@NotNull Function0 funTime) {
   Intrinsics.checkNotNullParameter(funTime, "funTime");
   long startTime = System.currentTimeMillis();
   funTime.invoke();
   String var3 = "Total Time " + (System.currentTimeMillis() - startTime);
   System.out.println(var3);
}
```

###### 使用内联inline .class
```Java

//将printFunTime 内的代码 copy 过来，减少对象的创建
public static final void main() {
    int $i$f$printFunTime = false;
    long startTime$iv = System.currentTimeMillis();
    int var3 = false;
    String var4 = "This is a fun";
    System.out.println(var4);
    String var5 = "Total Time " + (System.currentTimeMillis() - startTime$iv);
    System.out.println(var5);
 }

 // $FF: synthetic method
 public static void main(String[] var0) {
    main();
 }

 public static final void printFunTime(@NotNull Function0 funTime) {
    int $i$f$printFunTime = 0;
    Intrinsics.checkNotNullParameter(funTime, "funTime");
    long startTime = System.currentTimeMillis();
    funTime.invoke();
    String var4 = "Total Time " + (System.currentTimeMillis() - startTime);
    System.out.println(var4);
 }
```

###### 内联函数的return
在把函数作为高阶函数传递的时候，我们在lambda中，想return的时候，编辑器会提示`'return' is not allowed here`,
```Java
printFunTime {
     println("This is a fun")
     return@printFunTime
 }

 fun printFunTime(funTime: () -> Unit) {
   val startTime = System.currentTimeMillis()
   funTime()
   println("Total Time ${System.currentTimeMillis() - startTime}")
}
//并不会中断funTime()方法
// This is a fun
// Total Time 2
```
感兴趣的同学，可以看下class文件，和上面的是一样的，此处就不粘贴了。
但是，通过上面，我们知道`inline`会把 函数的代码复制到调用处，如果我们把函数加上`inline`就可以在lamdba中进行return,编译器不会报错，并且后面的代码 也不会执行。


### 不内联noline
正常使用，如果使用`inline`，那就是内联函数，那为什么还有有个`noline`关键字?
内联函数的「函数参数」 不允许作为参数传递给非内联的函数，
```Java
inline fun totalTime(noinline fun1Time: () -> Unit,fun2Time: () -> Unit,){
    printFunTime(fun1Time)
    //此处会报错
    printFunTime(fun2Time)
}

fun printFunTime(funTime: () -> Unit) {
    val startTime = System.currentTimeMillis()
    funTime()
    println("Total Time ${System.currentTimeMillis() - startTime}")
}
```


### 参考资料
- [koltin中文文档 ](https://book.kotlincn.net/text/inline-functions.html)
