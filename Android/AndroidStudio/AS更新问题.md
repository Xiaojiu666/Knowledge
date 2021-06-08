#一、AS自动检查更新
开发过程中，有时候会报一些Android Studio版本低要求升级之类的异常错误或者开发者想要升级到最新版本体验新版本新增的特性，如果重新在网上下载最新版本来安装不仅浪费时间还很麻烦，因此我们可以通过下载增量更新包或者AS自带的Updates来在原有的版本上进行升级，这里我们先介绍第二种方式来进行升级也就是通过AS自带的Updates来进行在线升级。

在打开的AS项目中选择File -> Settings… -> Updates，在右侧的窗口中选择Check Now按钮检查是否有最新版本，如果有最新版本就会弹出下图所示的对话框提示我们进行更新，选择Update and Restart更新并重启，重启完成后就能得到我们想要的最新版本了。

![](https://img-blog.csdn.net/2018062610113813?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L215X3JhYmJpdA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)


如上可以看到版本有以下4种渠道可以更新，他们的特点如下： 

-	1、Canary Channel：金丝雀版，能获取最新的功能（Bug较多且都还没有验证）。 
-	2、Dev Channel：开发者版，此版本大部分Bug都得到了解决。 
-	3、Beta Channel：测试版，这个版本很多小Bug都得到了解决，问题还是会有，但是基本上可能是我们遇不到的。 
-	4、Stable Channel：正式稳定版，这个版本是官方推荐的最新正式版，发布日期较晚，各项功能都很稳定，基本没有问题了，是大多数开发人员所青睐使用的版本。

如果使用增量包的方式去更新版本，可以在以下的链接中查看各渠道最新版本所支持的Patch包的详细信息进行选择。 

