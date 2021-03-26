

## 常用操作

#### 撤销

git reset --soft HEAD~1 撤回最近一次的commit(撤销commit，不撤销git add)

git reset --mixed HEAD~1 撤回最近一次的commit(撤销commit，撤销git add)

git reset --hard HEAD~1 撤回最近一次的commit(撤销commit，撤销git add,还原改动的代码)

##	SSH

https://blog.csdn.net/weixin_42752574/article/details/106367959







## QA

​	Q:LibreSSL SSL_connect: SSL_ERROR_SYSCALL in connection to github.com:443

​	A:git config --global http.sslBackend "openssl" 

