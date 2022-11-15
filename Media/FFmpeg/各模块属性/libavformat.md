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
