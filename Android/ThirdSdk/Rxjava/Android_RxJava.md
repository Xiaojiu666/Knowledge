





## 操作符



#### 创建型

######	just

just发射一组数据时，会把它当成一个整体，一次性发射。 

###### fromArray

fromArray用来创建一个Observable对象，可以将一个数组转化为可被观察的序列并且将它的数据逐个发射。

fromArray发射一组数据到观察序列中来时，它会先进行遍历，然后再逐个发射

https://blog.csdn.net/yuminfeng728/article/details/77494034



###### map

map只能进行一对一的变换。 主要是接收者，只会接受一次数据

######	flatMap

flatMap则可以进行一对一，一对多变换。接收者，只会接受一次数据，被转换数据可以转为 新的Observable

