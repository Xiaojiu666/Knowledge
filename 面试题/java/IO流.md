### java 中 IO 流分为几种？
-	输入流、输出流（按照流的方向进行分类）
-	字节流、字符流（按照读取的方式进行分类）

-	字节流输入流
```Java
private  static void byteInputSteam(){
			FileInputStream fileInputStream = null;
			try {
					 fileInputStream = new FileInputStream("C:\\Users\\41798\\Desktop\\ByteInputStream.txt");
					byte[] bytes=new byte[1024];
					int readCount=0;
					while((readCount = fileInputStream.read(bytes))!=-1) {
							System.out.print(new String(bytes,0,readCount));
					}
			} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
			} catch (IOException e){
					throw new RuntimeException(e);
			}finally {
					if (fileInputStream != null) {
							try {
									fileInputStream.close();
							} catch (IOException e) {
									throw new RuntimeException(e);
							}
					}
			}
	}
```  

-	字节输出流
```Java
private static void  byteOutputSteam(){
		 FileOutputStream fileOutputStream = null;
		 String helloWord = "ByteOutputStream";

		 try {
				 //以追加的方式在文件末尾写入。不会清空原文件的内容。
				 fileOutputStream= new FileOutputStream("C:\\Users\\41798\\Desktop\\ByteOutputStream.txt",true);
				 fileOutputStream.write(helloWord.getBytes());
				 fileOutputStream.flush();
		 } catch (FileNotFoundException e) {
				 throw new RuntimeException(e);
		 } catch (IOException e) {
				 throw new RuntimeException(e);
		 } if (fileOutputStream != null) {
				 try {
						 fileOutputStream.close();
				 } catch (IOException e) {
						 throw new RuntimeException(e);
				 }
		 }
 }
```
-	字符输出流
```Java
private  static void bufferInputSteam(){
		BufferedReader fileInputStream = null;
		try {
				String filePath = "C:\\Users\\41798\\Desktop\\ByteInputStream.txt";
				fileInputStream = new BufferedReader(new FileReader(filePath));
				byte[] bytes=new byte[1024];
				String line;
				while((line = fileInputStream.readLine())!=null) {
						System.out.print(line);
				}
				} catch (FileNotFoundException e) {
						throw new RuntimeException(e);
				} catch (IOException e){
						throw new RuntimeException(e);
				}finally {
				if (fileInputStream != null) {
						try {
								fileInputStream.close();
						} catch (IOException e) {
								throw new RuntimeException(e);
						}
				}
		}
}
```

-	字符输出流
```Java
private static void  bufferOutputSteam(){
		BufferedWriter fileOutputStream = null;
		String helloWord = "ByteOutputStream!!!!";
		try {
				//以追加的方式在文件末尾写入。不会清空原文件的内容。
				String filePath = "C:\\Users\\41798\\Desktop\\ByteOutputStream.txt";
				fileOutputStream= new BufferedWriter(new FileWriter(filePath));
				fileOutputStream.write(helloWord);
				fileOutputStream.flush();
				} catch (FileNotFoundException e) {
						throw new RuntimeException(e);
				} catch (IOException e) {
						throw new RuntimeException(e);
				} if (fileOutputStream != null) {
				try {
						fileOutputStream.close();
				} catch (IOException e) {
						throw new RuntimeException(e);
				}
		}
}

```
