## Snapshot
Compose 重组的重要桥接，用于管理`状态`和UI的重组。
######  官方翻译
可变状态和其他状态对象返回的值的快照。所有状态对象在快照中都将具有与创建快照时相同的值，除非它们在快照中显式更改。

###### 传统Compose 写法
```JAVA
@Composable
 fun Foo() {
  var text by remember { mutableStateOf("") }
  Button(onClick = { text = "$text\n$text" }) {
    Text(text)
  }
}
```
##


##
