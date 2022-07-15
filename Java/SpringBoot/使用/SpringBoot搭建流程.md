## Demo源码下载
对于有Java开发经验的同学，暂时不讲源码，Github上面有很多学习的例子，我使用的是[springboot-learning-example](https://github.com/JeffLi1993/springboot-learning-example)，直接Git clone。



## 配置Java环境
- [Ubuntu下运行jar包](https://www.csdn.net/tags/MtTakg1sNjg5MTktYmxvZwO0O0OO0O0O.html)

## 安装MongoDB
- [Ubuntu下安装](https://www.jb51.net/article/230768.htm)
- [MongoCopass可视化]()
- [测试](http:/服务器:27017）


## 安装Mysql
- [Ubuntu下安装配置](https://blog.csdn.net/qq_40039731/article/details/124675476)
- [卸载](https://www.jianshu.com/p/ea8b705f0915)
- [3306 端口连不上] 修改配置中的 127.0.0.1


## 防火墙
- [防火墙](https://blog.csdn.net/qq_44204058/article/details/109611297)
- [防火墙](https://www.cnblogs.com/EasonJim/p/6851241.html#:~:text=%E5%A6%82%E6%9E%9C%E4%BD%A0%E5%8F%91%E7%8E%B0%E7%8A%B6%E6%80%81%E6%98%AF%EF%BC%9A,inactive%2C%20%E6%84%8F%E6%80%9D%E6%98%AF%E6%B2%A1%E6%9C%89%E8%A2%AB%E6%BF%80%E6%B4%BB%E6%88%96%E4%B8%8D%E8%B5%B7%E4%BD%9C%E7%94%A8%E3%80%82)

## Xshell
- [Xshell](https://blog.csdn.net/qq_53639645/article/details/122542732)

## Reids

## Workbench
- [Workbench](https://blog.csdn.net/jsugs/article/details/124176899)

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

- 2、无法连接telnet

## 常见命令
MAC copy文件
- scp ~/Downloads/smart-logistics-0.0.1-SNAPSHOT.jar root@47.114.122.85:/smart
