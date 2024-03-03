配置多个SSH的目的：一台电脑可以让github、gitee、gitlab等账号同时存在，让不同账号配置不同的密钥。

创建SSH
多账号必须配置 config 文件（文件地址：C:\Users\用户名\.ssh，文件名：config）。
config文件内容
分别是配置 gitee、github、gitlab的内容
``` JAVA
    # gitee
    Host gitee.com # 代表gitee的git代码仓库地址
    HostName gitee.com
    PreferredAuthentications publickey
    IdentityFile ~/.ssh/id_rsa.gitee

    # github
    Host github.com # 代表github的git代码仓库地址
    HostName github.com
    PreferredAuthentications publickey
    IdentityFile ~/.ssh/id_rsa.github

    # gitlab
    Host 域名 # 代表公司gitlab的git代码仓库地址
    HostName 域名:8888
    PreferredAuthentications publickey
    IdentityFile ~/.ssh/id_rsa.gitlab
```
创建对应仓库文件
只要在 config 文件中声明了仓库，都需要创建对应的 rsa 文件
手动创建文件（文件名：id_rsa.gitlab，id_rsa.gitee，id_rsa.github）。如果不创建的话配置多平台时，在执行命令时会出错。


执行命令
config文件中配置多对应仓库，每个仓库都要执行这行命令，对应的仓库邮箱地址，仓库 rsa 文件
```
C:\Users\delo>ssh-keygen -t rsa -C "对应仓库邮箱地址@qq.com" -f ~/.ssh/id_rsa.gitee
```

查看生成的公钥
```
 ~/.ssh/id_rsa.gitee.pub
 ~/.ssh/id_rsa.github.pub
 ~/.ssh/id_rsa.gitlab.pub
 ```
公钥的内容类似于

将公钥的内容复制后分别配置到对应的远程仓库的SSH keys中


验证 key 是否正常工作
输入命令

```
ssh -T git@gitee.com
# 使用 github的话，则改为
ssh -T git@github.com
```

之后会问：

Are you sure you want to continue connecting (yes/no)?
输入 yes

出现上面的结果就表示配置成功了。

本地电脑目录结构
