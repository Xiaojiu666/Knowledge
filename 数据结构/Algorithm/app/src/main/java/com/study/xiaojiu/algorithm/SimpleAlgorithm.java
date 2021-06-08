package com.study.xiaojiu.algorithm;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SimpleAlgorithm extends BaseAlgorithm {
    public static final String TAG = "SimpleAlgorithm";


    /**
     * 无数学运算符加法(lintcode--1)
     * 无数学运算符时，应该想到使用逻辑运算符，
     * 与（&）： A&B    都为 1  结果为 1
     * 或（\）： A\b    只有一为1  结果为1
     * 异或（^）：A^B   相同为0  不同为1
     * 逻辑运算符有一个定论，当一个数字与另一个数字进行与（&）运算 结果为 0 时，他们的 亦或(^)运算结果必为他们的和
     * 6 = 0110
     * 9 = 1001
     * 与 等于0 ， 异或等于 和 15
     * <p>
     * 递归左移一位的目的就是让第一个数字 和第二位数字的 &运算的结果为0
     * 第一位数一直左移会对运算结果产生影响么？
     * 不会 因为第二位数一直在变化
     *
     * @param a
     * @param b
     * @return
     */
    public static int aplusb(int a, int b) {
        int c = a & b;
        int d = a ^ b;
        System.out.println(TAG + "C:" + c);
        System.out.println(TAG + "D:" + d);
        return c == 0 ? d : aplusb(c << 1, d);
    }


    /**
     * 计算出n阶乘中尾部零的个数(lintcode--2)
     * 设计一个算法，计算出n阶乘中尾部零的个数
     * 1. 方法一： 直接计算出该数的值，统计尾部的0 ，不推荐 耗时 并且结果会超出int的类型上限
     * 2. 方法二： 以5为迭代步数，  分析发现代码的时间复杂度实际是O(N/5)~=O(N)
     *
     * @param n
     * @return 参考资料：https://blog.csdn.net/surp2011/article/details/51168272
     */
    public static long trailingZeros(long n) {

        //方法二：分析发现代码的时间复杂度实际是O(N/5)~=O(N)
//        long count = 0;
//        long pwr = 25;
//        for (long temp = 5; temp <= n; temp += 5) {
//            // for循环内部的temp都是5的倍数，因此首先进行+1操作
//            count++;
//            pwr = 25;
//            // 判断是不是25、125、625...的倍数，并根据每次pwr的变化进行+1操作
//            while (temp % pwr == 0) {
//                count++;
//                pwr *= 5;
//            }
//        }
//        return count;


        long count = 0;
        long temp = n / 5;
        while (temp != 0) {
            count += temp;
            temp /= 5;
        }
        return count;

    }

    /**
     * 统计数字(lintcode--3)
     * 计算数字k在0到n中的出现的次数，k可能是0~9的一个值
     *
     * @param k
     * @param n
     * @return
     */

    public static int digitCounts(int k, int n) {
        // write your code here
        int count = 0 , x;
        if (k == 0 && n == 0) count = 1;
//        for (int i = 1;x = n / i;i *= 10) {
//            int high = x / 10;
//            if (k == 0) {
//                if (high) high--;
//                else {
//                    count++;
//                    break;
//                }
//            }
//            count += high * i;
//            int current = x % 10;
//            if (current > k) count += i;

//            else if (current == k) count += n - x * i + 1;
//        }
        return count;

    }


    /**
     * 快乐数(lintcode--488)
     * 快乐数（happy number）有以下的特性：
     * 在给定的进位制下，该数字所有数位(digits)的平方和，得到的新数再次求所有数位的平方和，如此重复进行，最终结果必为1。
     * 不唯一的就是非快乐数
     * 2 8 → 2²+8²=68 → 6²+8²=100 → 1²+0²+0²=1
     *
     * @param number
     * @return
     */
    public static boolean isHappyNumber(int number) {
        if (number == 1) {
            return true;
        }
        int sum = number;
        HashSet<Integer> resultSet = new HashSet<>();
        while (sum != 1) {
            //如果该元素已经包含在集合里 说明已经重复循环了
            if (resultSet.contains(sum)) {
                return false;
            }
            resultSet.add(sum);
            int temp = 0;
            //循环计算每一位的平方 总和
            while (sum != 0) {
                temp += (sum % 10) * (sum % 10);
                sum /= 10;
            }
            //将每一次的总和从新赋值，并且开始新的一轮循环计算
            sum = temp;
        }
        return true;
    }


    /**
     * 根据传入的数字 获取每一位的 数值
     *
     * @param number
     */
    static void getEveryBitNumber(int number) {
        int s = 0;
        while (number != 0) {

            //1. 通过取模 获取个位数值  129的 个位数就是 9
            //4. 此时number = 12 个位数就是 2 ，
            s = number % 10;

            //2. 因为已经取到个位数的数值 ，通过除以十 让整个number 右移一位  129 /10 = 12
            //5. 此时number = 12 已经取到个位数的2了 此时让number继续右移， 12/ 10 =1 ，重复操作 直至number = 0 说明 s 已经是最后一位了
            number /= 10;
            //3. 此时number已经右移完毕，并且从新赋值
        }
    }

    public static String flipWord(String sentence) {
        List list = Arrays.asList(sentence.split(" "));//一个空格
        Collections.reverse(list);
//        for (int i = 0; i < split.length; i++) {
//            split[i] = split[split.length - i - 1];
//        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }


        return list.toString();
    }

}
