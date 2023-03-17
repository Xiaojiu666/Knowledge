### object关键字 三大用处
1、对象表达式（Object Expression）  
2、伴生对象（Companion Object）  
3、对象声明（Object Expression）  



##### 对象声明（快速单例/不能包含构造）
```Java
object UserManager {
    fun saveUser()
}

// 反编译出的Java代码
public final class UserManager {
   public static final UserManager INSTANCE;

   public final void saveUser() {
   }

   private UserManager() {
   }

   static {
      UserManager var0 = new UserManager();
      INSTANCE = var0;
   }
}
  ```

##### 伴生对象(Java中的static方法)

```Java
class App {
    companion object {
        fun getAppContext() {}
    }
}

// 反编译出的Java代码
public final class App {
   public static final App.Companion Companion = new App.Companion((DefaultConstructorMarker)null);

   public static final class Companion {
      public final void getAppContext() {
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
```

##### 对象表达式(Java中的匿名内部类)
```Java
    var clickListener = object : ClickListener {
        override fun onClick() {

        }
    }

    interface ClickListener {
     fun onClick()
 }
```
