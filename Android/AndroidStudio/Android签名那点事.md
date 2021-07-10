## 1.Android签名那点事

## 2.release和debug的区别https://blog.csdn.net/wangjiang_qianmo/article/details/74528630

##  3.SHA1码

#### 3.1 作用
#### 3.2 查看-	1.签名版的sha1值:就是指上架APP，上线的时候需要的sha1值	-	通过jks签名		-	找到jks文件位置		-	输入命令：keytool -list -v -keystore collections.jks		-	2.未签名版的sha1值:就是值我们平时开发调试的时候用的sha1值:开发版的sha1和电脑的**有关系，不同电脑的sha1不同	-	通过系统.android文件获取		-	找到.android文件		-	输入keytool -list -v -keystore debug.keystore		-	输入密钥口令：密钥口令默认是android		-	3.通过系统打包的APK文件获取(共用)		-	把APK改成压缩文件格式		-	提取里面的META-INF/CERT.RSA		-	keytool -printcert -file 你的CERT.RSA路径


##  参考地址
-	1. https://blog.csdn.net/sanjay_f/article/details/48375233
-	 2. https://www.jianshu.com/p/f424ae12ddde
-	3. https://www.cnblogs.com/details-666/p/keystore.html  //如何签名
-  4. https://blog.csdn.net/gyh792054863/article/details/81044322 //如何运行签名版项目

1E:98:0C:CE:1B:C4:E2:80:24:49:FA:AF:01:50:B7:1C:F9:D9:98:F2
