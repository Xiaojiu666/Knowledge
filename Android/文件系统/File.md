


###### getExternalFilesDir() 和 getFilesDir()
    函数返回路径/storage/emulated/0/Android/data/包名/files
    用来存储一些长时间保留的数据,应用卸载会被删除

```JAVA
public String getFilesPath( Context context ){
    String filePath ;
    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
            || !Environment.isExternalStorageRemovable()) {
        //外部存储可用
        filePath = context.getExternalFilesDir(null).getPath();
    }else {
        //外部存储不可用
        filePath = context.getFilesDir().getPath() ;
    }
    return filePath ;
}
```


###### getExternalCacheDir() 和getCacheDir()
    函数返回路径/storage/emulated/0/Android/data/包名/cache
    用来存储一些临时缓存数据

```JAVA
public String getCachePath( Context context ){
        String cachePath ;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            cachePath = context.getExternalCacheDir().getPath() ;
        }else {
            //外部存储不可用
            cachePath = context.getCacheDir().getPath() ;
        }
        return cachePath ;
    }
```

###### 加External和不加的比较:
相同点:
1. 都可以做app缓存目录。
2. app卸载后，两个目录下的数据都会被清空。

不同点:
1. 目录的路径不同。前者的目录存在外部SD卡上的。后者的目录存在app的内部存储上。
2. 前者的路径在手机里可以直接看到。后者的路径需要root以后，用Root Explorer 文件管理器才能看到。
