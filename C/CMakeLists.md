####	CMakeLists.txt

######	cmake_minimum_required(`VERSION` `<min>`[...`<max>`] [`FATAL_ERROR`])

```c
//该命令指明了对cmake的最低(高)版本的要求，`...`为低版本和高版本之间的连接符号，没有其他含义。
cmake_minimum_required(VERSION 3.4.1)
```





######	SET(VAR [VALUE] [CACHE TYPE DOCSTRING [FORCE]]) 

```c
//定义so库和头文件所在目录，方面后面使用
set(ffmpeg_lib_dir ${CMAKE_SOURCE_DIR}/../libs/${ANDROID_ABI})
```

 



######	include_directories

添加头文件目录





######	add_library

```c
//该指令的主要作用就是将指定的源文件生成链接文件，然后添加到工程中去。该指令常用的语法如下：
add_library(<name> [STATIC | SHARED | MODULE]
            [EXCLUDE_FROM_ALL]
            [source1] [source2] [...])
```

其中<name>表示库文件的名字，该库文件会根据命令里列出的源文件来创建。而STATIC、SHARED和MODULE的作用是指定生成的库文件的类型。STATIC库是目标文件的归档文件，在链接其它目标的时候使用。SHARED库会被动态链接（动态链接库），在运行时会被加载。MODULE库是一种不会被链接到其它目标中的插件，但是可能会在运行时使用dlopen-系列的函数。默认状态下，库文件将会在于源文件目录树的构建目录树的位置被创建，该命令也会在这里被调用。

n一般添加分两种，一种是编译好的	*so*库，另一种是*cpp*文件

- so 库对应的 set_target_properties

######	set_target_properties

```C
//设置so库位置
set_target_properties( avutil
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libavutil.so )
```

- cpp源码 通过source1 source2指定



######	target_link_libraries

```c++
//该指令的作用为将目标文件与库文件进行链接。该指令的语法如下：
target_link_libraries(<target> [item1] [item2] [...]
                      [[debug|optimized|general] <item>] ...)
```

######	find_library(名称1 [path1 path2 …])

```C
//查找代码中使用到的系统库
find_library( # Sets the name of the path variable.
        log-lib
        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log )
```