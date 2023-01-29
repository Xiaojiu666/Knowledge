package com.study.algorithm.LeetCode;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DayOne {

//    public static void main(String[] args) {
////        int[] nums = {2, 7, 11, 15};
////        int[] nums2 = {2, 7, 11};
//
//        int[] nums = {1, 2, 8, 10, 11, 12, 13, 16};
//        int[] nums2 = {3, 6, 15, 20, 30};
////        int target = 18;
////        int[] x = twoSum1(nums, target);
////        System.out.println("x[0] " + x[0] + "x[1] " + x[1]);
//        String name = "abcabcdbbb";
////        System.out.println("size : " + getKthElement(nums, nums2, 7));
//
//    }



//    public String longestPalindrome(String s) {
//
//    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length, length2 = nums2.length;
        int totalLength = length1 + length2;
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            if (length1 > 0 && length2 > 0) {
                return getKthElement1(nums1, nums2, midIndex + 1);
            }
            if (length1 > 0) {
                return nums1[length1 / 2];
            }
            if (length2 > 0) {
                return nums2[length2 / 2];
            }
        } else {
            int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
            if (length1 > 0 && length2 > 0) {
                return (getKthElement1(nums1, nums2, midIndex1 + 1) + getKthElement1(nums1, nums2, midIndex2 + 1)) / 2.0;
            }
            if (length1 > 0) {
                return (nums1[length1 / 2 - 1] + nums1[length1 / 2]) / 2.0;
            }
            if (length2 > 0) {
                return (nums2[length2 / 2 - 1] + nums2[length2 / 2]) / 2.0;
            }
        }
        return 0;
    }

    private static double getKthElement1(int[] numM, int[] numN, int k) {
        // 5、由于存在边界问题 ，需要对比下长度，不能一直左移
        int lengthM = numM.length;
        int lengthN = numN.length;

        // 1、记录某个数组 需要`移除`的数据个数
        int removeMSize = 0, removeNSize = 0;

        while (true) {
            System.out.println("removeMSize : " + removeMSize + " , indexN : " + removeNSize + " K :" + k);

            // 6、边界情况 如果某个数组一直左移，说明这里面肯定没有第K小的值
            if (removeMSize == lengthM) {
                // 7、 查询N数组里面第K小的值
                return numN[removeNSize + k - 1];
            }
            if (removeNSize == lengthN) {
                return numM[removeMSize + k - 1];
            }

            if (k == 1) {
                return Math.min(numM[removeMSize], numN[removeNSize]);
            }
            // 2、记录第K小数值的 二分后左边的个数
            int kHalf = k / 2;
            // 3、我们需要对比的二分位置的数据
            // 由于存在需要前移数据，所以我们这里的索引需要+ 上一次前移的数据 -1 就是索引
            // 这里取最小值，是防止 数组过短，做二分的时候出边界
            int realIndexM = Math.min(kHalf + removeMSize, lengthM) - 1;
            int realIndexN =Math.min(kHalf + removeNSize, lengthN) - 1;
            // 4、对比 第K小数值 二分位置上的数据
            // 前移K值 和 控制索引
            // 直到移动到我们的们想要的K值
            if (numM[realIndexM] <= numN[realIndexN]) {
                k = k - (realIndexM - removeMSize + 1);
                removeMSize = realIndexM + 1;
            } else {
                k = k - (realIndexN - removeNSize + 1);
                removeNSize = realIndexN + 1;
            }
        }
    }


    private static double getKthElement(int[] nums1, int[] nums2, int k) {

        int length1 = nums1.length, length2 = nums2.length;
        int index1 = 0, index2 = 0;
        while (true) {
            System.out.println("index1 : " + index1 + " , index2 : " + index2 + " K :" + k);
            // 边界情况
            if (index1 == length1) {
                return nums2[index2 + k - 1];
            }
            if (index2 == length2) {
                return nums1[index1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }
//            System.out.println("k : " + k);
            // 正常情况
            int half = k / 2;
            int newIndex1 = Math.min(index1 + half, length1) - 1;
            int newIndex2 = Math.min(index2 + half, length2) - 1;
//            System.out.println("half : " + half + " , newIndex1 " + newIndex1 + " , newIndex2 " + newIndex2);
//            System.out.println("half : " + half + " , index1 " + index1 + " , index2 " + index2);
            int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
            if (pivot1 <= pivot2) {
                k -= (newIndex1 - index1 + 1);
                index1 = newIndex1 + 1;
            } else {
                k -= (newIndex2 - index2 + 1);
                index2 = newIndex2 + 1;
            }
        }
    }

    public static double cacluCenterValues(int[] nums) {
        double center;
        int length = nums.length;
        int i = length % 2;
        if (i == 0) {
            System.out.println("double");
            center = (double) nums[(length / 2) - 1] / 2 + (double) nums[(length / 2)] / 2;
        } else {
            System.out.println("single");
            center = (double) nums[(length - 1) / 2] / 2;
        }
        return center;
    }

    public static int[] twoSum1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int value = target - nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (value == nums[j]) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int value = target - nums[i];
            if (hashMap.containsKey(value)) {
                return new int[]{
                        hashMap.get(value), i};
            }
            hashMap.put(nums[i], i);
        }
        return new int[0];
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

//    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//        //1.创建预先指针（用来移动） 和头指针
//        ListNode preNode = null, headerNode = null;
//        //6.定义一个用来存放进位的
//        int carry = 0;
//        //2.循环取链表中的数据
//        while (l1 != null || l2 != null) {
//            //3.取值做加法计算
//            int n1 = l1 != null ? l1.val : 0;
//            int n2 = l2 != null ? l2.val : 0;
//            int total = n1 + n2 + carry;
//
//            //7.除法 取进位值 取模 取当前节点的真实数据
//            carry = total / 10;
//            int oValue = total % 10;
//
//            //4.指针存值 和移动
//            if (headerNode == null) {
//                headerNode = preNode = new ListNode(oValue);
//            } else {
//                preNode.next = new ListNode(oValue);
//                preNode = preNode.next;
//            }
//            //5.输入的原始链表向后移动
//            if (l1 != null) {
//                l1 = l1.next;
//            }
//            if (l2 != null) {
//                l2 = l2.next;
//            }
//        }
//        //8.如果最后一位的进位大于0 ，追加最后一个节点
//        if (carry > 0) {
//            preNode.next = new ListNode(carry);
//        }
//
//        return headerNode;
//    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //1. 输入数据判断，如果其中一个为空，返回另一组数据
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        //2.取值做加法计算，并存储节点中
        int total = l1.val + l2.val;
        ListNode head = new ListNode(total % 10);
        //3.递归绑定下一个节点数据
        head.next = addTwoNumbers(l1.next, l2.next);
        //4.由于问题的特性，每个位置的节点只能存一位长度的int，所以我们不用特别关注进位的问题.
        // 只要相加，如果有进位，只可能是1 所以只需要判断最终得节点，是否大于9 在末尾加上一个数据为1的节点
        // 如果问题改变一下，那就需要将进位值 单独计算，然后传入递归方法中进行
        if (total > 9) head.next = addTwoNumbers(head.next, new ListNode(1));
        return head;
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;
        //1.定义窗口用来存储最大值，定义Left用于记录左边框位置
        int max = 0;
        int left = 0;
        //2.定义HashMap 存储重复字符位置，出现重复字符串时，用于移动左边框
        HashMap<Character, Integer> tempMap = new HashMap<>();
        //3.循环遍历字符串，用于移动边框，并记录最窗口的值
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 4.2出现重复字符串时，将左边框移动，重新生成新的窗口
            if (tempMap.containsKey(c)) {
                left = Math.max(left, tempMap.get(c) + 1);
            }
            tempMap.put(c, i);
            // 4.1正常情况下 i - left + 1 为窗口的宽度，即是最大值
            max = Math.max(max, i - left + 1);
        }
        return max;
    }


}
