public class Solution {

    public static void main(String[] args) {
        int[] ints = twoSum(new int[]{1,7,11,2,8}, 9);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    public static int[] twoSum(int[] nums, int target) {
        // 整数数组
        int[] results = new int[2];
        for(int i = 0; i < nums.length - 1; i ++) {
            for(int j = i + 1; j < nums.length; j++ ) {
                if(nums[i] + nums[j] == target) {
                    results[0] = i;
                    results[1] = j;
                    return results;
                }
            }
        }
        return results;
    }

    public static int lengthOfLongestSubstring(String s) {
        return 0;
    }

}
