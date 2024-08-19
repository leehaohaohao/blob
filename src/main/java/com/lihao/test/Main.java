package com.lihao.test;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(findMin(new int[]{3,4,5,1,2}));

    }
    public static int findMin(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        Set<Integer> set = new HashSet<>();
        Integer[] test = new Integer[50];

        int n = nums.length;
        int l = 0;
        int r = n - 1;
        int min = 5001;
        while(l<=r){
            int mid = (l+r)/2;
            if(nums[l]<=nums[mid]){
                min = Math.min(nums[l],min);
                l=mid+1;
            }else{
                min = Math.min(nums[mid],min);
                r=mid-1;
            }
        }
        return min;
    }
}










