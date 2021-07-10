#### 什么是Surface
    处理由屏幕合成器管理的原始缓冲区。
    Surface 通常由图像缓冲区（如SurfaceTexture、MediaRecorder或分配）的使用者创建或从中创建，
    并交给某种制作者（如OpenGL、MediaPlayer或CameraDevice）来绘制。
    
    注意：Surface 的作用类似于对与其相关联的使用者的弱引用。它本身不会阻止其母消费者被回收
    
    
#### 什么是ImageReader
    ImageReader类允许应用程序直接访问呈现到曲面中的图像数据
    一些Android媒体API类接受表面对象作为渲染的目标，
    包括MediaPlayer、MediaCodec、CameraDevice、ImageWriter和RenderScript分配。
    可用于每个源的图像大小和格式各不相同
    图像数据封装在图像对象中，可以同时访问多个这样的对象，最多可以访问maxImages构造函数参数指定的数量。
    通过ImageReader的表面发送到ImageReader的新图像将排队，直到通过acquireLatestImage（）或acquireNextImage（）调用进行访问。
    由于内存限制，如果ImageReader无法以与生产速率相等的速率获取和释放图像，则图像源在尝试渲染到曲面时最终会暂停或丢弃图像。
    
    
#### SurfaceTexture
    从图像流中捕获帧作为OpenGL ES纹理。
    
    图像流可以来自摄像机预览或视频解码。
    从SurfaceTexture创建的曲面可以用作android.hardware.camera2、MediaCodec、MediaPlayer和分配API。
    调用updateTestEximage（）时，将更新创建SurfaceTexture时指定的纹理对象的内容，
    以包含来自图像流的最新图像。这可能会导致跳过流的某些帧
    
    在指定旧摄影机API的输出目标时，也可以使用SurfaceTexture代替SurfaceHolder。
    这样做将导致图像流中的所有帧被发送到SurfaceTexture对象，而不是设备的显示器。
