### 过滤器
![](/image/wireshark-过滤器.png)

```Java
TCP //过滤所有TCP
ip.src_host == 192.168.0.107 //指定源地址
ip.src_host == 192.168.0.107 and ip.dst_host == 192.168.1.1 // 与
ip.src_host == 192.168.0.107 or ip.dst_host == 192.168.1.1 //或者
```

### 参考资料
- [网络安全常用工具](https://www.bilibili.com/video/BV1Nu411v7gf?p=3&spm_id_from=pageDriver&vd_source=121fd996cc06804f8c80a7e27ab3cb5e)
