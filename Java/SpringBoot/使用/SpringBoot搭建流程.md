## Demo源码下载
对于有Java开发经验的同学，暂时不讲源码，Github上面有很多学习的例子，我使用的是[springboot-learning-example](https://github.com/JeffLi1993/springboot-learning-example)，直接Git clone。



## 配置Java环境
- [Ubuntu下运行jar包](https://www.csdn.net/tags/MtTakg1sNjg5MTktYmxvZwO0O0OO0O0O.html)

## 安装MongoDB
- [Ubuntu下安装](https://www.jb51.net/article/230768.htm)
- [MongoCopass可视化]()
- [测试](http:/服务器:27017）




## 问题
- 1、no main manifest attribute
    在pom.xml配置
```Java
<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <executable>true</executable>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
使用Maven LifeCycele 下的install去安装
-

## 常见命令
MAC copy文件
- scp ~/Downloads/smart-logistics-0.0.1-SNAPSHOT.jar root@47.114.122.85:/smart
