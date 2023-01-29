## 作用
用于各种音视频封装格式的生成和解析，包括获取解码所需信息以生成解码上下文结构和读取音视频帧等功能；


## 类
#### AVFormatContext
```Java
  // 读取视频文件信息
 AVFormatContext *ac = NULL;
 int ret = avformat_open_input(&ac, filepath, NULL, NULL);
 av_dump_format(ac, 0, filepaht, 0);
 // ac->duration
 // ac->bit_rate
 // ac->nb_streams //获取流
 int videoindex = -1;
 for (int i = 0; i < ac->nb_streams; i++) {
        if (ac->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
            videoindex = i;
            break;
        }
  }
 //获取流信息
 AVStream * pVStream = ac->streams[videoindex];

 avformat_close_input(&ac);


```

#### AVStream
```Java
pVStream-> nb_frames
pVStream->codecpar->width
```


## 方法
####  av_seek_frame

```Java
//AVFormatContext
//stream_index
//timestamp
// int64_t pts = time / av_q2d(time_base());
这里的timestamp代表的是想要移动到的起始位置的时间戳，注意这里是起始位置的时间戳，不是起始位置的秒数! 通俗地讲，它就是起始位置的pts，因此一个10s的视频，你想移动到5s的位置，直接传5是不对的。在 FFmpeg 中，时间戳(timestamp)的单位是时间基数(time_base)，时间戳值乘以时间基，可以得到实际的时刻值(以秒等为单位)。例如，如果一个视频帧的 dts 是 40，pts 是 160，其 time_base 是 1/1000 秒，那么可以计算出此视频帧的解码时刻是 40 毫秒(40/1000)，显示时刻是 160 毫秒(160/1000)。FFmpeg 中时间戳(pts/dts)的类型是int64_t 类型，如果把一个 time_base 看作一个时钟脉冲，那么 dts/pts 则可以看作是时钟脉冲的计数。
// AVSEEK_FLAG_BACKWARD
比方说总时长是1000ms, 那如果用户拖动到中间的位置，那就应该seek到500ms的位置，这个时候把500ms传入到上面的函数中进行seek就可以了。可这样有一个问题，你把500ms传入到上面的视频帧当中，是否真的有pts为500ms的视频帧呢？很难，有可能根本就没有pts为500ms的视频帧，这个时候可能有498，也有可能有501的，那到底是取498的呢？还是取501的呢？这个时候就要有一套策略，AVSEEK_FLAG_BACKWARD这个FLAG就相当于标识往后走，也就是找pts为501的视频帧。
//AVSEEK_FLAG_BYTE
这种对应的另外一种场景：假设我想移动视频到中间的位置，但是当前视频文件却没有索引, 不过我却知道这个视频文件的大小(1M), 那这个时候要移动视频到中间位置的话，其实就是应该对应在500KB左右的位置，AVSEEK_FLAG_BYTE这个FLAG就相当于是根据字节数来移动它的位置。
//AVSEEK_FLAG_ANY
移动视频到任意帧的位置。也就是说，seek到的位置可能是关键帧，也可能是非关键帧，注意：如果移动到的是非关键帧，这个时候解码可能会失败(因为少了前面的关键帧做参考)，出现的后果是可能会造成花屏。
//AVSEEK_FLAG_FRAME
移动视频到关键帧位置。与上面的FLAG不同，这种情况下会强制移动到关键帧位置，比如你要移动的位置是500ms, 但是在500ms没有关键帧，但是在前面400ms的位置有关键帧，它就会移到400ms的位置，这样的话画面就能够正常显示。
av_seek_frame(AVFormatContext  *s, int stream_index, int64_t timestamp,int flags);


```
