package com.sn.desnginpatterns.Single;

public class DoubleCheckSingle {

    private static volatile DoubleCheckSingle mDoubleCheckSingle = null;

    public static DoubleCheckSingle getInstace() {
        // 避免多个实例
        if (mDoubleCheckSingle == null) {
            // 当A线程和B线程到达此处时，B等待，A进入，A结束，B进入
            synchronized (DoubleCheckSingle.class) {
                // 此时B需要判断是否为空，不然多次创建实例
                if (mDoubleCheckSingle == null) {
                    mDoubleCheckSingle = new DoubleCheckSingle();
                    //  mDoubleCheckSingle = new DoubleCheckSingle(); 最终会变成三条指令
                    //  1.给 DoubleCheckSingle 分配内存
                    //  2.调用 DoubleCheckSingle 构造，初始化成员属性
                    //  3.将 mDoubleCheckSingle 对象指向已经分配好的内存 ，此时 mDoubleCheckSingle != null了
                    //  ↓--------------------------------  DCL JDK <= 1.5 --------------------------------------------↓
                    //  由于 java编译器 允许程序的乱序执行，以及JDK<1.5之前  JMM（java内存模型）中Cache、寄存器到内存回写顺序的规定，
                    //  上面2、3顺序是无法保证的，也就是说顺序会出现紊乱，
                    //  -------------------------------- DCL失效问题 --------------------------------------------
                    //  1-3-2情况: 3执行完毕，2未执行之前，切换成B线程，此时 mDoubleCheckSingle != null
                    //  但是 mDoubleCheckSingle 并未初始化完成，所以B使用拿到的 mDoubleCheckSingle就会出项问题
                    //  ↓--------------------------------  DCL JDK >1.5 --------------------------------------------↓
                    //  JDK > 1.6 为了解决DCL 加入了 volatile
                    //  单例只需要加入  private static volatile DoubleCheckSingle mDoubleCheckSingle = null;
                    //  就可以保证对象每次都从主内存进行读取，就可以正确的使用DCL方法
                    //  DCL的优点 :资源利用高，第一次执行get方法时，才会实例对象，效率高
                    //  DCL的缺点 :第一次加载会慢，后面会变快，
                }
            }
        }
        return mDoubleCheckSingle;
    }
}
