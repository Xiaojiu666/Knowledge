### sealed密封类
密封类用来表示受限的类继承结构：当一个值为有限几种的类型, 而不能有任何其他类型时。

在某种意义上，他们是枚举类的扩展：枚举类型的值集合 也是受限的，但每个枚举常量只存在一个实例，而密封类的一个子类可以有可包含状态的多个实例。
```JAVA
sealed class Result{
    data class ResultSuccess(val data: String) : Result()
    data class ResultFailed(val failedMsg: String, val failedCode: Int) : Result()
}

fun handleResult(result: Result) {
       when (result) {
           is ResultSuccess -> {
               //做网络请求成功处理
               Log.d("test",result.data)
           }
           is ResultFailed -> {
               //做网络失败处理
               Log.d("test",result.failedMsg +result.failedCode.toString() )
           }
       }
   }
```



### 与枚举的区别
- 对比枚举，密封类sealed算是枚举的增强，在可以用来区分类型的基础上，子类可拥有特殊的属性；
- 对比普通类，密封类sealed自动生成toString、hashCode、equals函数；
- 密封类，主构造函数的属性不能重写get、set函数；
- 当密封类sealed用于类型判断，when少了对应的类型处理（定义了3种，只处理了2种），编译期会有提示，也是很友好的。
