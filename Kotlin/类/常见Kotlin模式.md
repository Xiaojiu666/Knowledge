##### 属性委托
初始化属性时，您可能会重复 Android 的一些比较常见的模式，例如在 Fragment 中访问 ViewModel。为避免过多的重复代码，您可以使用 Kotlin 的属性委托语法。
```java
private val viewModel: LoginViewModel by viewModels()
```
