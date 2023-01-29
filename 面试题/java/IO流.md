### 面试题
##### 手写如何读取文件"input.txt"的内容，并写入到"output.txt" 中，写出核心代码(百度)
```java
-	字节流
		 File inputText = new File("I:\\input.txt");
		 File outputText = new File("I:\\output.txt");
		 try {
	            FileInputStream inputFileInputStream = new FileInputStream(inputText);
	            FileOutputStream outputFileOutPutStream = new FileOutputStream(outputText);
	            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputFileInputStream);
	            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputFileOutPutStream);
	            byte[] bys = new byte[1024];
	            int len = 0;
				//将输入缓冲区的数据读取出来，-1为节点，只要不为-1 就一直读
	            while ((len = bufferedInputStream.read(bys)) != -1) {
	                bufferedOutputStream.write(bys, 0, len);
	            }
	            bufferedOutputStream.flush();
	            inputFileInputStream.close();
	            outputFileOutPutStream.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
//	 字符流
			try {
            FileWriter fileWriter = new FileWriter(outputText);
            FileReader fileReader = new FileReader(inputText);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String readLine = "";
            readLine = bufferedReader.readLine();
            while (readLine != null) {
                bufferedWriter.write(readLine);
                readLine = bufferedReader.readLine(); // 一次读入一行数据
            }
            bufferedWriter.flush();
            fileWriter.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
```
