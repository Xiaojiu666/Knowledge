## 编译准备

![img](https://img-blog.csdn.net/20150722205737840)



#####	NDK https://developer.android.google.cn/ndk/downloads?hl=zh_cn （也可以通过AndroidStudio中download的NDK）

<img src="https://upload-images.jianshu.io/upload_images/2789400-c0576d3643348503.png?imageMogr2/auto-orient/strip|imageView2/2/w/1124" alt="img" style="zoom:50%;" />

######	关键路径：

```undefined
编译工具链目录：
toolchains/llvm/prebuilt/darwin-x86_64/bin

交叉编译环境目录：
toolchains/llvm/prebuilt/darwin-x86_64/sysroot
```

- 编译工具链目录：

<img src="https://upload-images.jianshu.io/upload_images/2789400-6a08709b0152d8fa.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200" alt="img" style="zoom:50%;" />



根据不同的CPU架构区和不同的Android版本，区分了不同的clang工具，根据自己需要选择就好了。

本文选择 CPU 架构 `armv7a`，Android版本 `21`:

```undefined
armv7a-linux-androideabi21-clang
armv7a-linux-androideabi21-clang++
```

- 编译环境目录：

  ![img](https://upload-images.jianshu.io/upload_images/2789400-589b41dd0ba25c79.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200)



好 到此NDK 配置准备完成

#### FFmpeg 源码 http://ffmpeg.org/download.html#get-sources

######	1.修改配置脚本Configure



#####	参考资料

- [FFmpeg so库编译](https://www.jianshu.com/p/350f8e083e82)
- [最简单的基于FFmpeg的移动端例子](https://blog.csdn.net/leixiaohua1020/article/details/47008825)

