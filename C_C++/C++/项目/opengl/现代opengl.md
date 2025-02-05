 

 ### API
 
### 顶点数组 
#### glGenBuffers(缓冲区个数，缓冲区ID指针  )
#### glBindBuffer(缓冲区类型GL_ARRAY_BUFFER，缓冲区ID指针)
#### glBufferData(缓冲区类型GL_ARRAY_BUFFER，数据字节大小， 数据，数据使用模式GL_STATIC_DRAW)   
数据使用模式：
-   GL_STATIC_DRAW ：数据几乎不变
-   GL_DYNAMIC_DRAW：数据经常改变
-   GL_STREAM_DRAW ：数据每次绘制时都改变

#### glDrawArrays(绘制模式，起始顶点索引，顶点个数) 
绘制模式：
-   GL_TRIANGLES：三角形
-   GL_TRIANGLE_STRIP：三角形带
-   GL_TRIANGLE_FAN：三角形扇
-   GL_LINES：线段
-   GL_LINE_STRIP：线段带
-   GL_LINE_LOOP：线段环
-   GL_POINTS：点
#### glDrawElements(绘制模式，元素个数，数据类型，元素数据偏移量)
#### glEnableVertexAttribArray(顶点属性索引)

### 顶点属性 
#### glEnableVertexAttribArray(顶点属性索引)
#### glVertexAttribPointer(顶点属性索引，顶点属性大小(1,2,3,4)，数据类型(GL_FLOAT,GL_INT,GL_UNSIGNED_INT),是否标准化(GL_TRUE,GL_FALSE),步长(0),顶点属性偏移量(0))
 
#### glUseProgram(程序ID)
#### glCreateShader(着色器类型)
#### glShaderSource  
#### glCompileShader
#### glCreateProgram  
#### glAttachShader
#### glLinkProgram
#### glUseProgram
   
### 着色器
#### glCreateShader(着色器类型)
#### glShaderSource
#### glCompileShader
#### glCreateProgram  
#### glAttachShader
#### glLinkProgram
#### glUseProgram
### 官网
[docs.gl](https://docs.gl/)