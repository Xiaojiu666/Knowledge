### ASM框架介绍


##### ClassWriter
- 类信息

    ClassWriter的父类是ClassVisitor，因此ClassWriter类继承了visit()、visitField()、visitMethod()和visitEnd()等方法。

- 构造
```Java
public class ClassWriter extends ClassVisitor {
    /* A flag to automatically compute the maximum stack size and the maximum number of local variables of methods. */
    public static final int COMPUTE_MAXS = 1;
    /* A flag to automatically compute the stack map frames of methods from scratch. */
    public static final int COMPUTE_FRAMES = 2;

    // flags option can be used to modify the default behavior of this class.
    // Must be zero or more of COMPUTE_MAXS and COMPUTE_FRAMES.
    public ClassWriter(final int flags) {
        this(null, flags);
    }
}
```

1、COMPUTE_MAXS: A flag to automatically compute the maximum stack size and the maximum number of local variables of methods. If this flag is set, then the arguments of the MethodVisitor.visitMaxs method of the MethodVisitor returned by the visitMethod method will be ignored, and computed automatically from the signature and the bytecode of each method.

2、COMPUTE_FRAMES: A flag to automatically compute the stack map frames of methods from scratch. If this flag is set, then the calls to the MethodVisitor.visitFrame method are ignored, and the stack map frames are recomputed from the methods bytecode. The arguments of the MethodVisitor.visitMaxs method are also ignored and recomputed from the bytecode. In other words, COMPUTE_FRAMES implies COMPUTE_MAXS.
小总结：

3、COMPUTE_MAXS: 计算max stack和max local信息。

4、COMPUTE_FRAMES: 既计算stack map frame信息，又计算max stack和max local信息。

推荐使用COMPUTE_FRAMES参数
换句话说，COMPUTE_FRAMES是功能最强大的：

    COMPUTE_FRAMES = COMPUTE_MAXS + stack map frame



- 基本方法



### AdviceAdapter



### 其他知识点


参数对应

| 字符 | Java类型 | C类型
| :----: | :----: | :----:|
| V | void | void |
|Z | jboolean | boolean |
| I | jint | int |
|J | jlong | long |
|D | jdouble | double |
|F | jfloat | float |
|B | jbyte | byte |
|C | jchar | char |
|S | jshort | short |



| 字符 | Java类型 | C类型
| :----: | :----: | :----:|
| [I  | jintArray | int[] |
|[F | jfloatArray | float[] |
| [B | jint | int |
|[C | jlong | long |
|[S | jdouble | double |
|[D | jfloat | float |
|[J | jbyte | byte |
|[Z | jchar | char |

上面的都是基本类型。如果Java函数的参数是class，则以"L"开头，以";"结尾中间是用"/" 隔开的包及类名。而其对应的C函数名的参数则为jobject. 一个例外是String类，其对应的类为jstring
Ljava/lang/String; String jstring
Ljava/net/Socket; Socket jobject
如果JAVA函数位于一个嵌入类，则用$作为类名间的分隔符。
例如 "(Ljava/lang/String;Landroid/os/FileUtils$FileStatus;)Z"





### 参考资料
- [ClassWriter介绍](https://blog.csdn.net/cold___play/article/details/124690032)
- [AdviceAdapter介绍](https://blog.csdn.net/cold___play/article/details/124832055)
- [字节码插桩框架ASM（一）](https://blog.csdn.net/qq_23992393/article/details/103677719)
- [Gradle插件<第二篇>：ASM解析](https://www.jianshu.com/p/197fa07466e6)
