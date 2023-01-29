### Hexo介绍
### 前期准备工具
[Git](https://git-scm.com/downloads)
[Hexo](https://hexo.io/zh-cn/)
[Node.js](https://nodejs.org/zh-cn/)
[Github](https://github.com/)
### 搭建流程
###### 1、新建一个文件夹，在文件夹根目录 使用
```
npm install hexo-cli -g
```
###### 2、安装完成后，输入 hexo -v 输出版本信息
```
hexo -v
```
###### 3、输入hexo init初始化,自动会在根目录下生成Hexo所需要的文件和文件夹
```
hexo init
```
###### 4、 输入命令 hexo generate可以缩写hexo g 生成HTML页面,并且会多出public文件夹，public文件夹内是hexo生成的html文件还有css、js、json
```
hexo generate
```
###### 5、使用命令 hexo server 可缩写hexo s启动服务
```
hexo server
```
###### 6、在浏览器里输入http://localhost:4000这是默认主题，后面可以在网上下载别人做好的主题进行修改


### 使用Github托管
###### 1、登录Github新建一个仓库，仓库名必须为你的Github用户名.github.io
```
例如"GUOXU.github.io"
```
###### 2、配置SSH(细节可以在网上查找)
###### 3、进到新建的Github仓库 点击右上角setting进入设置找到Deploykeys选择 Add Deploy keys Title随便填写 Key的内容填刚才id_rsa.pub文件中复制的内容
###### 4、用记事本打开的Hexo项目文件夹下单_config.yml文件，拉到最底部找到deploy:填写如下内容(注意:冒号后面有个空格)
###### 5、进入你的Hexo目录，分别执行以下命令
```
    $ npm install hexo-deployer-git --save  #安装部署工具
    $ hexo clean                            #清除缓存       可缩写hexo c
    $ hexo generate                         #生成静态文件    可缩写hexo g
    $ hexo deploy                           #部署到Github   可缩写hexo d
```
###### 6、在浏览器中输入http://你的Github用户名.github.io 就可以看到你的个人博客



### 参考资料
- [Hexo搭建个人博客-并部署到Github上托管](https://zhuanlan.zhihu.com/p/137476045)
- [hexo 搭建自己的个人博客以及配置主题](https://blog.csdn.net/weixin_53893220/article/details/122930456)
