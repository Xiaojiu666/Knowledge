## 常见概念

######	图像

> 一帧

######	片组

> 是一个编码图像中若干宏块的一个子集，包含一个或若干个片。
>
> 一般一个片组中，每片的宏块是按扫描次序进行编码的，除非使用任意片次序（Arbitrary Slice Order, ASO）一个编码帧中的片之后可以跟随任一解码图像的片。

######	片(Slice)

> 一帧视频图像可编码成一个或者多个片，每片包含整数个宏块，即每片至少一个宏块，最多时包含整个图像的宏块。
>
> 片的目的：为了限制误码的扩散和传输，使编码片相互间保持独立。片共有5种类型：I片（只包含I宏块）、P片（P和I宏块）、B片（B和I宏块）、SP片（用于不同编码流之间的切换）和SI片（特殊类型的编码宏块）。
>
> 以下是片的句法结构：片头规定了片的类型、属于哪个图像、有关的参考图像等；片的数据包含了一系列宏块和不编码数据。
>
> 片组是一个编码图像中若干宏块的一个子集，包含一个或若干个片。
>
> ![img](https://p-blog.csdn.net/images/p_blog_csdn_net/wanggp_2007/EntryImages/20091120/slice.png)
>
> 一般一个片组中，每片的宏块是按扫描次序进行编码的，除非使用任意片次序（Arbitrary Slice Order, ASO）一个编码帧中的片之后可以跟随任一解码图像的片。



######	宏块(MB)

> 是H.264编码的基本单位，一个编码图像首先要划分成多个块（4x4 像素）才能进行处理，显然**宏块应该是整数个块组成**，通常宏块大小为16x16个像素。
>
> 宏块分为I、P、B宏块:
>
> I宏块只能利用当**前片中已解码**的像素作为参考进行帧内预测；
>
> P宏块可以利用**前面已解码**的图像作为参考图像进行帧内预测；
>
> B宏块则是利用**前后向的参考图形**进行帧内预测
>
> 在I帧中只有I宏块，因此只能进行帧内预测
>
> 在P中可以存在I宏块和P宏块，进行帧间预测，前向预测
>
> 在B帧中存在I,P,B宏块，可以进行帧内，前向预测，后向预测

![img](https://img-blog.csdn.net/20130920133014546?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGVpeGlhb2h1YTEwMjA=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

######	NALU

H.264 码流格式，编码后的数据流，文件后缀.h264

每一个NALU单元通过连续存储类型/参考集/IDR等，通过几个NALU构成一帧，其中每个NALU之间通过startcode（起始码）进行分隔，起始码分成两种：0x000001（3Byte）或者0x00000001（4Byte）。如果NALU对应的Slice为一帧的开始就用0x00000001，否则就用0x000001。 通过startcode 可以分析出，帧和帧之间的数据和参考集等信息。





####	参考资料

- [雷神博客](https://blog.csdn.net/leixiaohua1020/article/details/50534369)