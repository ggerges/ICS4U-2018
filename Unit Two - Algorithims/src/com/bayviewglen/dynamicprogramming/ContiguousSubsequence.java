package com.bayviewglen.dynamicprogramming;

public class ContiguousSubsequence {

	public static void main(String[] args) {
		
		
		int[] nums = {5,15,-30,10,-5,40,10};
		
		int maxSum = getMaxSum(nums);
		
		System.out.println(maxSum);

	}

	private static int getMaxSum(int[] nums) {
		
		int maxSum = 0;//current max sum
		
		int[] solution = new int[nums.length+1];
		solution[0] = 0; 
		
		for(int i =1; i <solution.length; i++) {
			
				solution[i] = Math.max(solution[i-1]+nums[i-1],nums[i-1]);
				if(solution[i] > maxSum) {
					maxSum = solution[i];
			
				
			}
			
		}
	
		return maxSum;
	
	}

}
