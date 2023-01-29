### 什么是ViewBading
    通过视图绑定功能，您可以更轻松地编写可与视图交互的代码。在模块中启用视图绑定之后，
    系统会为该模块中的每个 XML 布局文件生成一个绑定类。绑定类的实例包含对在相应布局中具有
    ID 的所有视图的直接引用。
    用于替换findviewbyID

### 用法
##### 基础用法
  1.配置/依赖
```Java
  //在某个模块中启用视图绑定
  android {
          ...
          viewBinding {
              enabled = true
          }
    }
```
  2.更改需要绑定的视图根目录为<layout> 标签，必须，否则无法自动生成对应的binding类
```Java
  <layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
      <androidx.constraintlayout.widget.ConstraintLayout
          style="?attr/catalogToolbarStyle"
          android:layout_width="match_parent"
          android:layout_height="match_parent">
      </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```  
  3 使用
```Java
  private lateinit var binding: ResultProfileBinding

     override fun onCreate(savedInstanceState: Bundle) {
         super.onCreate(savedInstanceState)
         binding = ResultProfileBinding.inflate(layoutInflater)
         val view = binding.root
         setContentView(view)
     }

    binding.name.text = viewModel.name
    binding.button.setOnClickListener { viewModel.userClicked() }
```

### 问题
##### 与 findViewById 的区别
与使用 findViewById 相比，视图绑定具有一些很显著的优点：
- Null 安全：由于视图绑定会创建对视图的直接引用，因此不存在因视图 ID 无效而引发 Null 指针异常的风险。此外，如果视图仅出现在布局的某些配置中，则绑定类中包含其引用的字段会使用 @Nullable 标记。
- 类型安全：每个绑定类中的字段均具有与它们在 XML 文件中引用的视图相匹配的类型。这意味着不存在发生类转换异常的风险。



### 参考资料
