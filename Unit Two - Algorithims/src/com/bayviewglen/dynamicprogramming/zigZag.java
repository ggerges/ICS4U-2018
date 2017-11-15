package com.bayviewglen.dynamicprogramming;



public class zigZag {

	public static void main(String[] args) {

		int[] sequence = { 1,2,1,1,1,3,1,2,2,3,2,4,3,5,3,5,5,5,3,4,3,4,3,4,2,2,3,2,3,2,3,2,3,2,3,2};

		System.out.println(solveZigZag(sequence));
	}

	private static int solveZigZag(int[] sequence) {

		int[] solution = new int[sequence.length];
		int[] maxes = new int[sequence.length];
		maxes[0] = 2;
		int max = 1;

		for (int i = 1; i < maxes.length; i++) {

			if (sequence[i - 1] > sequence[i]) {
				solution[i] = 1;
			} else if (sequence[i - 1] < sequence[i]) {
				solution[i] = -1;
			} else {
				solution[i] = solution[i - 1];
			}
			if (solution[i] != solution[i - 1]) { // zig zag sequence
				maxes[i] = maxes[i - 1] + 1;
				if (maxes[i] > max) {
					max = maxes[i];
				}
			} else {
				maxes[i] = 1;
			}
		}

		return max;

	}

}
