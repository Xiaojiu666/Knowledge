## 作用
声音/图像编解码,基本是最重要的库
```Java
//通过format 库获取
AVCodecParameters *pCodecParam=pVStream->codecpar;
AVCodec * codec = avcodec_find_decoder(pCodecParam->codec_id);
```



### 参考资料
- [android ffmpeg提取视频中的i帧和p帧](https://blog.csdn.net/baidu_41666295/article/details/103460497)
