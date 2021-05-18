## PeerConnection
    PeerConnection也就是Peer-to-Peer connection(P2P), 就是两个"人"的连接. 双方分别创建PeerConnection对象, 
    然后向对方发送自己的网络状况ICE和多媒体编码格式SDP(因为这时候连接还没建立, 所以发送内容是通过服务器完成的).
    当双方网络和编码格式协商好后, 连接就建立好了, 这时从PeerConnection中能获取到对方的MediaStream数据流, 也就能播放对方的音视频了.
    

####    信令
    会话控制，网络和媒体信息
    WebRTC使用RTCPeerConnection在浏览器（也称为对等设备）之间通信流数据，但还需要一种机制来协调通信并发送控制消息，
    该过程称为信令。WebRTC 未指定信令方法和协议：信令不是RTCPeerConnection API的一部分。
    
    相反，WebRTC应用程序开发人员可以选择他们喜欢的任何消息协议，例如SIP或XMPP，以及任何适当的双工（双向）通信通道。
    所述appr.tc示例使用XHR和通道API作为信令机制。我们构建的代码实验室使用在Node服务器上运行的Socket.io。
    
    信令用于交换三种类型的信息：
    会话控制消息：初始化或关闭通信并报告错误。
    网络配置：对于外界，我的计算机的IP地址和端口是什么？
    媒体功能：我的浏览器及其要与之通信的浏览器可以处理哪些编解码器和分辨率？
    在开始对等流传输之前，必须成功完成通过信令进行的信息交换。

##	ICE
	Interactive Connectivity Establishment, 交互式连接建立. 其实是一个整合STUN和TURN的框架,
	 给它提供STUN和TURN服务器地址, 它会自动选择优先级高的进行NAT穿透.

##	SDP
	Session Description Protocol: 会话描述协议. 发送方的叫Offer, 接受方的叫Answer, 
	除了名字外没有区别. 就是一些文本描述本地的音视频编码和网络地址等.
	
	
##  STUN服务器  
    STUN服务器用于寻找客户端的公网IP, 让两个服务端通过公网IP直接发送音视频数据, 这些数据不经过STUN服务器. 
    因此STUN服务器的数据流量很小, 免费的服务器很多. 不保证所有情况下都能建立WebRTC连接.
    https://upload-images.jianshu.io/upload_images/1896166-c30ba9a7ec1572e6.png?imageMogr2/auto-orient/strip|imageView2/2/w/834
    
##  TURN服务器
    TURN服务器用于直接转发音视频数据, 当客户端网络情况特殊, 无法相互发送数据时. 
    经过它的数据量很大, 基本没有免费的. 只要客户端能访问到TURN服务器就能建立WebRTC连接.

## STUN&&TURN 区别
    https://www.cnblogs.com/mlgjb/p/8243690.html
    
##  多人会议
    
####  Mesh 网格
        每个人都跟其他人单独建立连接. 4个人的情况下, 每个人建立3个连接, 
        也就是3个上传流和3个下载流. 此方案对客户端网络和计算能力要求最高, 对服务端没有特别要求. 
  
####  SFU(Selective Forwarding Unit)  
        可选择转发单元, 有一个中心单元, 负责转发流. 每个人只跟中心单元建立一个连接, 
        上传自己的流, 并下载别人的流. 4个人的情况下, 每个人建立一个连接,
         包括1个上传流和3个下载流. 此方案对客户端要求较高, 对服务端要求较高.
        
####  MCU
        MCU(Multipoint Control Unit) 多端控制单元, 有一个中心单元, 负责混流处理和转发流. 
        每个人只跟中心单元建立一个连接, 上传自己的流, 并下载混流. 4个人的情况下, 每个人建立一个连接, 包括1个上传流和1个下载流. 
        此方案对客户端没有特别要求, 对服务端要求最高.
        
   

####  [class]MediaStream
    可以访问数据流，例如来自用户的相机和麦克风的数据流
    
####  [class]PeerConnection
    PeerConnection是WebRTC组件，用于处理对等点之间的流数据的稳定和高效的通信。
    以下是WebRTC架构图，显示了RTCPeerConnection的角色。
D:\Doc\MyDoc\AndroidStudyDocument\image\webRTC_image.jpg
![Image text](../image/webRTC_image.jpg)  
 
    从该图中可以理解的主要事情是RTCPeerConnection使Web开发人员免受潜在的各种复杂问题的困扰。
    WebRTC所使用的编解码器和协议进行了大量工作，即使在不可靠的网络上也可以进行实时通信：
    丢包隐藏
    回声消除
    带宽适应性
    动态抖动缓冲
    自动增益控制
    降噪抑制
    图像“清洁”。
    
## 参考资料
-   https://www.html5rocks.com/en/tutorials/webrtc/basics/#toc-disruptive
-   https://webrtc.googlesource.com/src/                //源码下载
-   https://www.xiaochao.me/tool/144.html

    

