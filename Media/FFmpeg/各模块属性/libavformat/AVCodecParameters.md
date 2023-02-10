### 重要参数
```C++
enum AVMediaType codec_type; 编解码器的类型
const struct AVCodec *codec; 编解码器,初始化后不可更改
enum AVCodecID codec_id; 编解码器的id
int64_t bit_rate; 平均比特率
uint8_t *extradata; int extradata_size; 针对特定编码器包含的附加信息
AVRational time_base; 根据该参数可以将pts转化为实践
int width, height; 每一帧的宽和高
int gop_size; 一组图片的数量，编码时用户设置，解码时不使用
enum AVPixelFormat pix_fmt; 像素格式，编码时用户设置，解码时可由用户指定，但是在分析数据会被覆盖用户的设置
int refs; 参考帧的数量
enum AVColorSpace colorspace; YUV色彩空间类型
enum AVColorRange color_range; MPEG JPEG YUV范围
int sample_rate; 采样率 仅音频
int channels; 声道数（音频）
enum AVSampleFormat sample_fmt; //采样格式
int frame_size; 每个音频帧中每个声道的采样数量
int profile;配置类型
int level;级别
```
