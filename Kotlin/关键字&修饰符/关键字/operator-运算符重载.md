### 运算符重载
允许我们将所有的运算符,关键字进行重载,扩展他们本身的用法。  
语法:
```Java
1、必须是扩展函数 或者是某个类中的方法
2、必须有一个参数
operator fun ClassName.plus(obj: Obj): Obj {
  //处理相加的逻辑
}
```
eg : 通过 `+号` 对 两个 `Person对象` 进行 拼接运算


```Java
operator fun Person.plus(person:Person):String{
    return person.name +" and "+ this.name
}
data class Person(var name:String,var age: Int)

fun main() {
   var personAndroid =  Person("Android",15)
   var personKotlin =  Person("Kotlin",20)
    val plus = personAndroid + personKotlin
    println("plus ${plus}")
}
```

|  语法糖表达式	   | 实际调用函数|
| :----:  | :----: |
|a + b	|a.plus(b)|
|a - b	|a.minus(b)
|a * b	|a.times(b)
|a / b	|a.div(b)
|a % b	|a.rem(b)
|a ++	|a.inc()
|a –|	a.dec()
|+a	|a.unaryPlus()
|-a	|a.unaryMinus()
|!a	|a.not()
|a == b|	a.equals(b)
|a > b |
|a < b	|
|a >= b	|a.compareTo(b)|
|a <= b	||
|a…b|	a.rangeTo(b)|
|a[b]|	a.get(b)|
|a in b |	b.contains(a)|
|a[b] = c |	a.set(b,c)|
