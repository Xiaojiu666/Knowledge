### 记录
- 1、clone [源码](git@github.com:rogersce/cnpy.git)
- 2、找到电脑NDK目录 `..\Android\Sdk\ndk\21.0.6113669`
- 3、将jni目录复制进去，执行  `.\ndk-build.cmd`
- 4、生成.a 静态库

- 5.生成静态库步骤
- 5.1 修改android.mk文件中的配置 `include $(BUILD_STATIC_LIBRARY)` 为  `include $(BUILD_SHARED_LIBRARY)`






### 问题
- Q: Defaulting to minimum supported version android-16.
- A: Application.MK 中添加`APP_PLATFORM := android-16`
- Q: set APP_ALLOW_MISSING_DEPS=true to allow missing dependencies.
- A: Application.MK 中添加`APP_ALLOW_MISSING_DEPS := true`
- Q: Android NDK: Module png depends on undefined modules: -lz
- A: 使用android studio编译Android.mk,需要使用LOCAL_LDLIBS（链接的库不产生依赖关系，一般用于不需要重新编译的库），不能使用LOCAL_SHARED_LIBRARIES（生成依赖关系，当库不存在时会去编译这个库）
