package com.gx.task;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        int[] nums = new int[]{-1000, 999, -4, 8, -9, 20, -30, 20, -100,};
        int[] nums1 = new int[]{1, 4, 7, 2, 5, 8, 6};
        int[] nums2 = new int[]{7, 2, 6};
        int[] nums3 = new int[]{7, 1, 5, 3, 6, 4};
//        System.out.println("Test :  " + maxSubArrayA(nums));
//        System.out.println("Test :  " + maxSubArrayB(nums));
//        System.out.println("Test :  " + containsDuplicate(nums));
//        printArray(intersect(nums1, nums2));
        printArray(translate(nums3, 10));
//        maxProfit(nums3);
    }

//    public static boolean containsDuplicate(int[] nums) {
//        if (nums == null || nums.length == 0) {
//            return false;
//        }
//        List<Integer> datas = new ArrayList<>(nums.length);
//        for (int i = 0; i < nums.length; i++) {
//            int num = nums[i];
//            if (datas.contains(num)) {
//                return true;
//            } else {
//                datas.add(num);
//            }
//        }
//        return false;
//    }


    public static int[] translate(int[] nums, int k) {
        int[] newNums = new int[nums.length + k];
        for (int i = 0; i < nums.length; i++) {
            newNums[i + k] = nums[i];
        }
        return newNums;
    }


    public static void printArray(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }

    public static int maxSubArrayA(int[] nums) {
        // Arrays.sort(nums);
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int maxNumSub = nums[0];
        for (int i = 0; i < nums.length; i++) {
            int currentSub = 0;
            for (int j = i; j < nums.length; j++) {
                currentSub += nums[j];
                maxNumSub = Math.max(maxNumSub, currentSub);
            }
        }
        return maxNumSub;
    }

    //-1000, 999, -4, 8, -9, 20, -30, 20, -100
    public static int maxSubArrayB(int[] nums) {
        int res = nums[0];
        int sum = 0;
        for (int num : nums) {
            if (sum > 0)
                sum += num;
            else
                sum = num;
            res = Math.max(res, sum);
        }
        return res;
    }

    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> reult = new HashSet<Integer>();
        for (int i : nums) {
            reult.add(i);
        }
        return reult.size() != nums.length;
    }


    public static int maxProfit(int[] prices) {
        int maxValues = Math.max(prices[0], prices[1]);
        int maxValuesPosition = prices[0] > prices[1] ? 0 : 1;
        int minValues = Math.min(prices[0], prices[1]);
        int minValuesPosition = prices[0] < prices[1] ? 0 : 1;
        for (int i = 1; i < prices.length - 1; i++) {
            if (prices[i] > maxValues) {
                maxValues = prices[i];
                maxValuesPosition = i;
            } else if (prices[i] < minValues) {
                if (i > maxValuesPosition) {
                    maxValues = Math.max(prices[i], prices[i + 1]);
                    maxValuesPosition = prices[i] > prices[i + 1] ? i : i + 1;
                    minValues = Math.min(prices[i], prices[i + 1]);
                    minValuesPosition = prices[i] < prices[i + 1] ? i : i + 1;
                } else {
                    minValues = prices[i];
                    minValuesPosition = i;
                }

            }
        }
        System.out.println("maxValues " + maxValues + "maxValuesPosition " + maxValuesPosition);
        System.out.println("minValues " + minValues + "minValuesPosition " + minValuesPosition);
        return maxValues - minValues;
    }


    public static int[] intersect(int[] nums1, int[] nums2) {
        if (nums2.length > nums1.length) {
            return intersect(nums2, nums1);
        }
        List<Integer> list1 = new ArrayList<>();
        for (int num : nums1) {
            list1.add(num);
        }
        List<Integer> list2 = new ArrayList<>();
        for (int num : nums2) {
            if (list1.contains(num)) {
                list2.add(num);
                // 从 list1 除去已匹配的数值
                list1.remove(Integer.valueOf(num));
            }
        }
        int[] res = new int[list2.size()];
        int i = 0;
        for (int num : list2) {
            res[i++] = num;
        }
        return res;
    }

    public static int[] intersectA(int[] nums1, int[] nums2) {
        if (nums2.length > nums1.length) {
            return intersectA(nums2, nums1);
        }
        HashSet<Integer> hashSet = new HashSet<>();
        for (int num : nums1) {
            hashSet.add(num);
        }
        List<Integer> list2 = new ArrayList<>();
        for (int num : nums2) {
            if (hashSet.contains(num)) {
                list2.add(num);
                // 从 list1 除去已匹配的数值
                hashSet.remove(Integer.valueOf(num));
            }
        }
        int[] res = new int[list2.size()];
        int i = 0;
        for (int num : list2) {
            res[i++] = num;
        }
        return res;
    }


}
