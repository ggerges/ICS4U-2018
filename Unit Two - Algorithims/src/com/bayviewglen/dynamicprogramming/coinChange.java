package com.bayviewglen.dynamicprogramming;

public class coinChange {

	public static void main(String[] args) {
		System.out.println(change(10,new int[] {2,3,5}));

	}

	private static int change(int amount, int[] coins) {
		int [] combinations = new int[amount+1];
		
		combinations[0] = 1;
		
		for(int coin:coins) { //goes through coins
			System.out.println("Coin : " + coin );
			for(int i = 1; i<combinations.length; i++) { //goes through array combinations with each coin
				if(i >= coin) {
					combinations[i] += combinations[i-coin];
					printAmount(combinations);
				}
			}
			System.out.println();
		}
		
		
		return combinations[amount];
	}
	
	
	public static void printAmount(int[] arr) {
		for(int i = 0; i<arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

}
