##1.下载使用

- 1.账号密码本地配置

   我的电脑 - 属性 - 高级系统设置 - 环境变量 - 新建变量变量名HOME，变量值%USERPROFILE%

  ![](http://www.runoob.com/wp-content/uploads/2015/03/271117384245853.png)

- 2.创建账号密码文件

   开始 - 运行 中打开%Home%，即windows的管理员账号文件夹。新建一个名为"_netrc"的文件，填写你要保存的服务器地址及账号密码，保存。

![](http://www.runoob.com/wp-content/uploads/2015/03/271123307214691.png)
![](http://www.runoob.com/wp-content/uploads/2015/03/271123401437312.png)

##2.Github命令
####2.1简单初始化版本库
	1. git init 						//把当前文件夹 初始化为git仓库
	2. git add “fileName” 				//添加一个文件到 git仓库里
	3. git commit -m “更新内容”			//提交添加的文件到git仓库


####2.2版本操作控制
	1. git reset --hard “commit_id”		//回滚到指定版本号上
	2. git log							//查看提交的历史版本
	3. git reflog						//查看历史操作命令
	4. git reset --hard HEAD^			//回滚到上一版本
	5. head								//表示当前版本
	6. git checkout 					//检出

####2.3远程仓库操作
	1. git remote add origin https: //将本地的仓库关联到GitHub  remote(远程)  origin(源头)
	2. git pull origin master		//上传github之前pull一下
	3. git push -u origin master		//上传代码到GitHub远程仓库
	4. git pull origin               //拉取远端所愿仓库代码

	####2.4分支管理
	1. 查看分支：git branch
	2. 创建分支：git branch <name>
	3. 切换分支：git checkout <name>
	4. 创建+切换分支：git checkout -b <name>
	5. 合并某分支到当前分支：git merge <name>
	6. 删除分支：git branch -d <name>
	7. git log --graph					//查看分支合并图
	8. git stash						//隐藏当前分支，作用:如果不隐藏子分支，当主分支出现问题的时候，切换主分支的时候，子分支的内容会出现在主分支里面
	9. git stash pop					//回复隐藏内容
	10. git branch -D <name>			//删除分支
	11. git branch --set-upstream-to origin/master  //git 设置当前分支 更新流从远端的哪个分支


####2.5标签
	1. git tag <name>					//为分支打一个标签
	2. git tag							//查看标签
	3. git tag version commit_id		//为一个版本打一个标签
	4. 命令git push origin <tagname>可以推送一个本地标签
	5. 命令git push origin --tags可以推送全部未推送过的本地标签
	6. 命令git tag -d <tagname>可以删除一个本地标签
	7. 命令git push origin :refs/tags/<tagname>可以删除一个远程标签

##3.GitGUI

	1.克隆已有项目


	-2.检出项目

-
		1.先来设置与远程地址的关联，Git remote：
![](http://www.runoob.com/wp-content/uploads/2015/03/271242561118002.png)
![](http://www.runoob.com/wp-content/uploads/2015/03/271244220336453.png)

		 填写SSH地址与项目名。下面有3个选项：
		第一个：立刻获取最新改动（所以如果是本地克隆远程一个项目，也可以这样操作）。
		第二个：本地新建的项目，初始化远程仓库并发布过去。
		第三个：什么也不做。
	.在项目的进行过程中，获取仓库的最新改动Git fetch

![](http://www.runoob.com/wp-content/uploads/2015/03/271259025495085.png)
![](http://www.runoob.com/wp-content/uploads/2015/03/271300329558531.png)


		3.合并
		请注意啦，不管你本地有没有代码，fetch之后呢，是都要merge的，
		也就是说，fetch下来后，大大的代码还在一个小黑屋里，我们需要把它装到自己兜里。
		选择合并 - 本地合并，然后选择本地的分支（如果你没有创建分支，则只有1个主支master） !]http://www.runoob.com/wp-content/uploads/2015/03/271254126278757.png)

		4.冲突处理（Conflict）
	合的过程中可能会出现一些红色的文件与一堆叹号，这时候慌慌张张的点啥它都不管用，不用担心，不是程序坏了，
		只是有冲突的文件，例如A童鞋写了width:1180px，你写了width:auto。那到底用你们谁的呢。
		在GUI界面正文区，正文区右键可以选择，Use local version（使用本地版本）或Use remote version（使用远程版本），
		到底用你的还是小伙伴的？或者你也可以自己再整合。

!]htp://www.runoob.com/wp-content/uploads/2015/03/271257485644055.png)



##4.参考资料

-	1.https://www.runoob.com/w3cnote/git-gui-window.html//
-	https://blog.csdn.net/guotianqing/article/details/82391665          //git中submodule子模块的添加、使用和删除 @ 数字



##  5.常用组合

远端仓库有文件未忽略，添加忽略配置未生效解决方案
    git rm -r --cached .                    //重置Git仓库
    git add .                               //重新Add所有文件
    git commit -m “update .gitignore"       // commit
    git push



## 推送新分支内容到远端

1.创建本地分支dev git branch dev
2.切换到分支dev git checkout dev
3.查看远程分支 git branch -a
4.将代码暂存在本地 git add .
5.给暂存在本地的代码打个tag git commit -am "first commit"
6.将本地代码推送到远程指定分支 git push origin <指定的分支名>

##  5.问题

-  1.Q :fatal: refusing to merge unrelated histories





-  1.A :https://www.centos.bz/2018/03/git-%E5%87%BA%E7%8E%B0-fatal-refusing-to-merge-unrelated-histories-%E9%94%99%E8%AF%AF/

-  2.Updates were rejected because the tip of your current branch is behind
-  2.https://www.jianshu.com/p/004f47f908c5





## 常用操作

#### 撤销

git reset --soft HEAD~1 撤回最近一次的commit(撤销commit，不撤销git add)

git reset --mixed HEAD~1 撤回最近一次的commit(撤销commit，撤销git add)

git reset --hard HEAD~1 撤回最近一次的commit(撤销commit，撤销git add,还原改动的代码)

##	SSH

https://blog.csdn.net/weixin_42752574/article/details/106367959



##### [git 拉取远程分支到本地](https://www.cnblogs.com/jiafeimao-dabai/p/9957532.html)



## QA

​	Q:LibreSSL SSL_connect: SSL_ERROR_SYSCALL in connection to github.com:443

​	A:git config --global http.sslBackend "openssl"
