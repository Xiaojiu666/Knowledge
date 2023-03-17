### 基础用法
-     主构造函数需要至少有一个参数；
-     主构造函数的所有参数需要标记为 val 或 var；
-     数据类不能被abstract、open、sealed或者internal修饰  

```JAVA
  data class UserInfo(
      val name: String = "",
      var age: Int = 10,
      var grade: Int = 6,
)
```

```JAVA
public final class ModelA {
   @NotNull
   private final String name;
   private int age;
   private int grade;

   @NotNull
   public final String getName() {
      return this.name;
   }

   public final int getAge() {
      return this.age;
   }

   public final void setAge(int var1) {
      this.age = var1;
   }

   public final int getGrade() {
      return this.grade;
   }

   public final void setGrade(int var1) {
      this.grade = var1;
   }

   public ModelA(@NotNull String name, int age, int grade) {
      Intrinsics.checkNotNullParameter(name, "name");
      super();
      this.name = name;
      this.age = age;
      this.grade = grade;
   }

   // $FF: synthetic method
   public ModelA(String var1, int var2, int var3, int var4, DefaultConstructorMarker var5) {
      if ((var4 & 1) != 0) {
         var1 = "";
      }

      if ((var4 & 2) != 0) {
         var2 = 10;
      }

      if ((var4 & 4) != 0) {
         var3 = 6;
      }

      this(var1, var2, var3);
   }

   public ModelA() {
      this((String)null, 0, 0, 7, (DefaultConstructorMarker)null);
   }

   @NotNull
   public final String component1() {
      return this.name;
   }

   public final int component2() {
      return this.age;
   }

   public final int component3() {
      return this.grade;
   }

   @NotNull
   public final ModelA copy(@NotNull String name, int age, int grade) {
      Intrinsics.checkNotNullParameter(name, "name");
      return new ModelA(name, age, grade);
   }

   // $FF: synthetic method
   public static ModelA copy$default(ModelA var0, String var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = var0.name;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.age;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.grade;
      }

      return var0.copy(var1, var2, var3);
   }

   @NotNull
   public String toString() {
      return "ModelA(name=" + this.name + ", age=" + this.age + ", grade=" + this.grade + ")";
   }

   public int hashCode() {
      String var10000 = this.name;
      return ((var10000 != null ? var10000.hashCode() : 0) * 31 + Integer.hashCode(this.age)) * 31 + Integer.hashCode(this.grade);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof ModelA) {
            ModelA var2 = (ModelA)var1;
            if (Intrinsics.areEqual(this.name, var2.name) && this.age == var2.age && this.grade == var2.grade) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
```


data数据类帮我们生成了equals()、hashCode()、toString()、copy()函数。


##### copy(java的clone)
当需要复制一个对象，并需要改变部分属性值时，copy()函数为此而生。
```JAVA
val modelA = ModelA(name = "A", age = 10, grade = 1)
val modelB = modelA.copy(name = "B")

log("modelA:$modelA,hashCode:${modelA.hashCode()}")
log("modelB:$modelB,hashCode:${modelB.hashCode()}")
//执行结果：
modelA:ModelA(name=A, age=10, grade=1),hashCode:62776
modelB:ModelA(name=B, age=10, grade=1),hashCode:63737
```
