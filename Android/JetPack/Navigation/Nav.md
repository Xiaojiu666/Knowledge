###	 什么是 “导航”

官方:导航是指支持用户导航、进入和退出应用中不同内容片段的交互。

其实就是用于管理多个Fragment交互

使用导航组件由以下三个关键部分组成：

- 导航图：在一个集中位置包含所有导航相关信息的 XML 资源。这包括应用内所有单个内容区域（称为*目标*）以及用户可以通过应用获取的可能路径。
- `NavHost`：显示导航图中目标的空白容器。导航组件包含一个默认 `NavHost` 实现 ([`NavHostFragment`](https://developer.android.google.cn/reference/androidx/navigation/fragment/NavHostFragment))，可显示 Fragment 目标。
- `NavController`：在 `NavHost` 中管理应用导航的对象。当用户在整个应用中移动时，`NavController` 会安排 `NavHost` 中目标内容的交换。

导航的好处(官方介绍)：

- 自动处理 Fragment 事务。
- 默认情况下，正确处理往返操作。
- 为动画和转换提供标准化资源。
- 实现和处理深层链接。
- 包括导航界面模式（例如抽屉式导航栏和底部导航），用户只需完成极少的额外工作。
- [Safe Args](https://developer.android.google.cn/guide/navigation/navigation-pass-data#Safe-args) - 可在目标之间导航和传递数据时提供类型安全的 Gradle 插件(自动生成)。
- `ViewModel` 支持 - 您可以将 `ViewModel` 的范围限定为导航图，以在图表的目标之间共享与界面相关的数据。

主要作用便于Fragment跳转，不用通过Fragment事务处理，可以在Fragment跳转时加入动画



###	普通使用

#####	配置/依赖

```groovy
dependencies {
  def nav_version = "2.3.5"

  // Java language implementation
  implementation "androidx.navigation:navigation-fragment:$nav_version"
  implementation "androidx.navigation:navigation-ui:$nav_version"

  // Kotlin
  implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
  implementation "androidx.navigation:navigation-ui-ktx:$nav_version"

  // Feature module Support
  implementation "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"

  // Testing Navigation
  androidTestImplementation "androidx.navigation:navigation-testing:$nav_version"

  // Jetpack Compose Integration
  implementation "androidx.navigation:navigation-compose:2.4.0-alpha03"
}
```

#####	使用

- 导航图

  1. 在“Project”窗口中，右键点击 res 目录，然后依次选择 New > Android Resource File。此时系统会显示 New Resource File 对话框。
  2. 在 File name 字段中输入名称，例如“nav_graph”。
  3. 从 Resource type 下拉列表中选择 Navigation，然后点击 OK。

    android:name 属性包含 NavHost 实现的类名称。
    app:navGraph 属性将 NavHostFragment 与导航图相关联。导航图会在此 NavHostFragment 中指定用户可以导航到的所有目的地。
    app:defaultNavHost="true" 属性确保您的 NavHostFragment 会拦截系统返回按钮。请注意，只能有一个默认 NavHost。如果同一布局（例如，双窗格布局）中有多个宿主，请务必仅指定一个默认 NavHost。

- 添加NavHost容器

    用于装载Fragment的容器
    ```java
    <?xml version="1.0" encoding="utf-8"?>
      <androidx.constraintlayout.widget.ConstraintLayout
          xmlns:android="http://schemas.android.com/apk/res/android"
          xmlns:app="http://schemas.android.com/apk/res-auto"
          xmlns:tools="http://schemas.android.com/tools"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          tools:context=".MainActivity">

          <androidx.appcompat.widget.Toolbar
              .../>

          <androidx.fragment.app.FragmentContainerView
              android:id="@+id/nav_host_fragment"
              android:name="androidx.navigation.fragment.NavHostFragment"
              android:layout_width="0dp"
              android:layout_height="0dp"
              app:layout_constraintLeft_toLeftOf="parent"
              app:layout_constraintRight_toRightOf="parent"
              app:layout_constraintTop_toTopOf="parent"
              app:layout_constraintBottom_toBottomOf="parent"
              app:defaultNavHost="true"
              app:navGraph="@navigation/nav_graph" />

          <com.google.android.material.bottomnavigation.BottomNavigationView
              .../>
      </androidx.constraintlayout.widget.ConstraintLayout>
    ```
  - <FragmentContainerView>标签
  -
- 跳转到目的地(调用)

  导航到目的地是使用 NavController 完成的，它是一个在 NavHost 中管理应用导航的对象。每个 NavHost 均有自己的相应 NavController。您可以使用以下方法之一检索 NavController：

      NavHostFragment.findNavController(Fragment)
      Navigation.findNavController(Activity, @IdRes int viewId)
      Navigation.findNavController(View)

  - 普通ID跳转
```Java
  //普通版本
  viewTransactionsButton.setOnClickListener { view ->
    //重载方法，有多个构造，可传参数
   view.findNavController().navigate(R.id.viewTransactionsAction)
 }
 //快捷版本，源码和上面方法一样
 button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.next_fragment, null))
```
  -  Safe Args 跳转(推荐，安全性高)

    此插件可生成简单的对象和构建器类，以便在目的地之间实现类型安全的导航。我们强烈建议您在导航以及在目的地之间传递数据时使用 Safe Args。

    1.主gradle配置下
```Java
buildscript {
        repositories {
            google()
        }
        dependencies {
            val nav_version = "2.3.5"
            classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        }
}
```

    2.应用模块
```Java
  apply plugin: "androidx.navigation.safeargs"        //生成适用于 Java 模块或 Java 和 Kotlin 混合模块的 Java 语言代码
  apply plugin: "androidx.navigation.safeargs.kotlin" //仅 Kotlin 模块的 Kotlin 语言代码
```
    3.自动生成action(FragmentName+Directions)
```Java
override fun onClick(v: View) {
          val amount: Float = ...
          val action =
              SpecifyAmountFragmentDirections
                  .actionSpecifyAmountFragmentToConfirmationFragment(amount)
          v.findNavController().navigate(action)
}
```
  - 使用 [DeepLinkRequest](https://developer.android.google.cn/guide/navigation/navigation-deep-link#implicit) 导航(建议直接看文档)

    使用 navigate(NavDeepLinkRequest) 直接导航到隐式深层链接目的地
```Java
    val request = NavDeepLinkRequest.Builder
      .fromUri("android-app://androidx.navigation.app/profile".toUri())
      .build()
  findNavController().navigate(request)
```

### 侧拉/底部导航
### QA
- 1.不自动生成Safe Args
- A:在导航图里面配置action
### 参考资料
- [Android官方导航组件](https://developer.android.google.cn/guide/navigation)
