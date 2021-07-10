##  为什么要Binder

## AIDL语法

####    为什么要AIDL语言
   AIDL 是 Android 接口定义语言。
   用来解决跨进程通信.   
    
####    AIDL 语法
-   文件类型：xx.aidl
-   支持类型:
        Java中的八种基本数据类型，包括 byte，short，int，long，float，double，boolean，char。
        String 类型。 CharSequence类型。
        List类型：List中的所有元素必须是AIDL支持的类型之一，或者是一个其他AIDL生成的接口，或者是定义的parcelable（下文关于这个会有详解）。List可以使用泛型。
        Map类型：Map中的所有元素必须是AIDL支持的类型之一，或者是一个其他AIDL生成的接口，或者是定义的parcelable。Map是不支持泛型的
        
-   定向tag:表示跨进程通信中数据的流向。 
        in: 数据只能从客户端流向服务端
        out：数据只能从服务端流向客户端
        inout: 表示可以在服务端和客户端之间双向流通。in 为定向 tag 的话表现为服务端将会接收到一个那个对象的完整数据，
        但是客户端的那个对象不会因为服务端对传参的修改而发生变动；out 的话表现为服务端将会接收到那个对象的的空对象，
        但是在服务端对接收到的空对象有任何修改之后客户端将会同步变动；inout 为定向 tag 的情况下，服务端将会接收到客户端传来对象的完整信息，
        并且客户端将会同步服务端对该对象的任何变动。
        
-   两种AIDL文件：
            用来定义parcelable对象，以供其他AIDL文件使用AIDL中非默认支持的数据类的
            定义接口方法


