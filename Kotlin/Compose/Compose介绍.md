### 前言

### 什么是Compose
###### 官方文档
  ​​Jetpack Compose​​ 是在 2019 Google I/O 大会上发布的新库，直到 2021 年 7 月发布 release 版本 1.0.0。它的特点是可以用更少的 Kotlin 代码，更便捷地在 Android 平台上完成 UI 的开发。

###### 特性
Compose 声明的 UI 不可变，无法被外界引用，无法持有状态，用 ​​@Composable​​ 声明以一个“纯函数”的方式运行，当 State 变化时函数重新执行刷新 UI，可以更好地贯彻声明式 UI 的特点。  

相对于Compose , 传统XML可以拿到TextView的引用，通过set/get 对一些属性配置，但Compose 无法拿到任何View的引用，通过Remember、`UiState` 等存储数据的地方，对数据更新，从而达到UI的改变。

- 传统 XML代码
```JAVA
    var tv: TextView = findViewById(R.id.tv)  
    tv.setColor(red)
```
- 声明式 Compose代码
```JAVA
    val name by remember {
         mutableStateOf("GX")
     }
// 当改变 name 状态值，就会自动更新 UI 状态
    Text(text =  "hello ${name}",
            modifier = Modifier.background(color = Color.Blue)
        )
```


### 状态管理State



###### 参考资料
- [Android Compose 的使用](https://blog.51cto.com/u_15548643/5153791)
