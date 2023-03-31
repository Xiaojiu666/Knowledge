




##### rememberSaveable
rememberSaveable 是 Jetpack Compose 中的一个函数，它可以帮助我们保存状态。当我们使用 rememberSaveable 时，Compose 会将状态保存在 Bundle 中，以便在重建时恢复状态。
```JAVA
  //该remember下，页面旋转，会记录
   var count by rememberSaveable { mutableStateOf(0) }
   //该remember下，页面旋转，不会记录状态值
   //var count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) {
        Text("I've been clicked $count times")
    }
```
