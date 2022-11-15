#	libavcodec
声音/图像编解码,基本是最重要的库

######	AVCodec

1、使用`avcodec_find_decoder`创建



###### [结构体]AVCodecContext



######	AVPacket

保存的是解码前的数据，也就是压缩后的数据。该结构本身不直接包含数据，其有一个指向数据域的指针，FFmpeg中很多的数据结构都使用这种方法来管理数据，

存储压缩数据（视频对应H.264等码流数据，音频对应AAC/MP3等码流数据），简单来说就是携带一个NAL视频单元，或者多个NAL音频单元。 AVPacket保存一个NAL单元的解码前数据，该结构本身不直接包含数据，其有一个指向数据域的指针。

```c
    AVBufferRef *buf; //当前AVPacket中压缩数据的引用计数，以及保存压缩数据的指针地址(压缩数据申请的空间在这里)
    uint8_t *data;//保存压缩数据的指针地址（data同时指向了buf中的data）
    int   size;//压缩数据的长度
    int   stream_index;//视频还是音频的索引
```
使用通常离不开下面4个函数:

1、使用`av_packet_alloc`来创建一个`AVPacket`的实例，但该函数并不会为数据分配空间，其指向数据域的指针为NULL。

2、使用`av_read_frame`将流中的数据读取到`AVPacket`中。

3、使用`av_packet_unref`减少数据域的引用技术，当引用技术减为0时，会自动释放数据域所占用的空间。

4、使用`av_packet_free`来释放`AVPacket`本身所占用的空间。



```c++
AVCodec *av_codec_next(const AVCodec *c);
```

AVCodec -> type  获取解码器类型(AVMEDIA_TYPE_VIDEO视频/AVMEDIA_TYPE_AUDIO 音频)

AVCodec -> name   编解码器实现的名称，等于支持的格式



```c++
int avcodec_send_packet(AVCodecContext *avctx, const AVPacket *avpkt);
```



```c++
//从解码器中获取解码的输出数据
//@参数 avctx 编码上下文
//@参数 frame 这将会指向从解码器分配的一个引用计数的视频或者音频帧（取决于解码类型）
//@注意该函数在处理其他事情之前会调用av_frame_unref(frame)
//0：成功，返回一帧数据
int avcodec_receive_frame(AVCodecContext *avctx, AVFrame *frame);
```





####	libavformat

###### [结构体]AVFormatContext

```
AVPacket是FFmpeg中很重要的一个数据结构，它保存了解复用之后，解码之前的数据（仍然是压缩后的数据）和关于这些数据的一些附加信息，如显示时间戳（pts）、解码时间戳（dts）、数据时长，所在媒体流的索引等。

对于视频（Video）来说，AVPacket通常包含一个压缩的Frame，而音频（Audio）则有可能包含多个压缩的Frame。并且，一个Packet有可能是空的，不包含任何压缩数据，只含有side data（side data，容器提供的关于Packet的一些附加信息。例如，在编码结束的时候更新一些流的参数）。

AVPacket的大小是公共的ABI（public ABI）一部分，这样的结构体在FFmpeg很少，由此也可见AVPacket的重要性。它可以被分配在栈空间上（可以使用语句AVPacket packet; 在栈空间定义一个Packet ），并且除非libavcodec 和 libavformat有很大的改动，不然不会在AVPacket中添加新的字段。
```

**常见变量**：

AVPacket中的字段可用分为两部分：数据的缓存及管理，关于数据的属性说明。

- 关于数据的属性有以下字段：

  - pts 显示时间戳
  - dts 解码时间戳
  - stream_index Packet所在stream的index
  - flats 标志，其中最低为1表示该数据是一个关键帧
  - duration 数据的时长，以所属媒体流的时间基准为单位
  - pos 数据在媒体流中的位置，未知则值为-1
  - convergence_duration 该字段已被deprecated，不再使用

- 数据缓存，AVPacket本身只是个容器，不直接的包含数据，而是通过数据缓存的指针引用数据。AVPacket中包含有两种数据

  - data 指向保存压缩数据的指针，这就是AVPacket实际的数据。（视频对应H.264等码流数据，音频对应AAC/MP3等码流数据）

  - side_data 容器提供的一些附加数据

  - buf 是AVBufferRef类型的指针，用来管理data指针引用的数据缓存的，其使用在后面介绍。



视频的时长可以转换成HH:MM:SS的形式，示例代码如下：

```c
if(pFormatCtx->duration != AV_NOPTS_VALUE){
		int hours, mins, secs, us;
		int64_t duration = pFormatCtx->duration + 5000;
		secs = duration / AV_TIME_BASE;
		us = duration % AV_TIME_BASE;
		mins = secs / 60;
		secs %= 60;
		hours = mins/ 60;
		mins %= 60;
		printf("%02d:%02d:%02d.%02d\n", hours, mins, secs, (100 * us) / AV_TIME_BASE);
}
```



**相关函数**

操作AVPacket的函数大约有30个，主要可以分为：AVPacket的创建初始化、AVPacket中的data数据管理（clone，free，copy等）、AVPacket中的side_data数据管理。
AVPacket的创建有很多种，而由于Packet中的数据是通过data引用的，从一个Packet来创建另一个Packet有多种方法。

- `av_read_frame` 这个是比较常见的了，从媒体流中读取帧填充到填充到Packet的数据缓存空间。如果`Packet->buf`为空，则Packet的数据缓存空间会在下次调用`av_read_frame`的时候失效。这也就是为何在[FFmpeg3：播放音频](http://www.cnblogs.com/wangguchangqing/p/5788805.html)中，从流中读取到Packet的时，在将该Packet插入队列时，要调用`av_dup_avpacket`重新复制一份缓存数据。（`av_dup_avpacket`函数已废弃，后面会介绍）
- `av_packet_alloc` 创建一个AVPacket，将其字段设为默认值（data为空，没有数据缓存空间）。
- `av_packet_free` 释放使用`av_packet_alloc`创建的AVPacket，如果该Packet有引用计数（packet->buf不为空），则先调用`av_packet_unref(&packet)`。
- `av_packet_clone` 其功能是 `av_packet_alloc` + `av_packet_ref`
- `av_init_packet` 初始化packet的值为默认值，该函数不会影响data引用的数据缓存空间和size，需要单独处理。
- `av_new_packet` `av_init_packet`的增强版，不但会初始化字段，还为data分配了存储空间。
- `av_copy_packet` 复制一个新的packet，包括数据缓存。
- `av_packet_from_data` 初始化一个引用计数的packet，并指定了其数据缓存。
- `av_grow_packet`和 `av_shrink_packet` 增大或者减小Packet->data指向的数据缓存。



#	libavutil
包含一些公共的工具函数
######	AVFrame

结构体描述了解码后的（原始）音频或视频数据。

通常被分配一次，然后多次重复使用以持有不同的数据（例如，单个AVFrame持有从解码器接收的帧）。 在这种情况下，av_frame_unref()将释放帧所持有引用，再次重用之前将其重置为初始状态。

1、使用`av_frame_alloc`分配AVFrame并将其字段设置为默认值。主要该函数只分配AVFrame的空间，

# libavformat
用于各种音视频封装格式的生成和解析，包括获取解码所需信息以生成解码上下文结构和读取音视频帧等功能；

# libswscale
用于视频场景比例缩放、色彩映射转换；视频像素数据的格式装换；

# libpostproc
用于后期效果处理；

# libswresample
音频采样格式数据转换；

# libavfilter
滤镜特效处理，如水印

# libavdevice
各种设备的输入输出


### 参考资料
- [分类](https://blog.csdn.net/qq_30124547/article/details/90708340)
- [ffmpeg获取视频信息](https://blog.csdn.net/qq_41824928/article/details/103631719)
