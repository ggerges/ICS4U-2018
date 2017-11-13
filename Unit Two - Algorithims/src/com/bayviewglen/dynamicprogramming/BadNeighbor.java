package com.bayviewglen.dynamicprogramming;

public class BadNeighbor {

	public static void main(String[] args) {
		
		int[] neighbors = {11,15}; 
		
		int maxDonations = getMaxDonations(neighbors);
		System.out.println(maxDonations);
	}

	private static int getMaxDonations(int[] neighbors) {
		
		if(neighbors.length >2) {
		
		int[] topDown = new int[neighbors.length-1]; // -1 because dont want it to count last neighbor
		topDown[0] = neighbors[neighbors.length-1];
		topDown[1] = neighbors[neighbors.length-1];
		
		int[] bottomUp = new int[neighbors.length-1];
		bottomUp[0] = neighbors[0];
		bottomUp[1] = neighbors[0];
		
		
		for(int i = 2; i<neighbors.length-1; i++) {
			topDown[i] = Math.max(topDown[i-2] + neighbors[neighbors.length-1 -i], topDown[i-1]) ;
			bottomUp[i] = Math.max(bottomUp[i-2] + neighbors[ i], bottomUp[i]);
			
		}
		return Math.max(topDown[topDown.length-1], bottomUp[bottomUp.length-1]);
	}else {
		return Math.max(neighbors[0], neighbors[neighbors.length-1]);
	}
	
	}
	
}
 