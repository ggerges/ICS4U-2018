package com.bayviewglen.graphs;


import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class graph {
	
	public static void main(String[] args) throws FileNotFoundException {
		
	
		int[][] edgesOfVertices = buildgraph();
		
		printGraph(edgesOfVertices);

	}

	private static void printGraph(int[][] edgesOfVertices) {

		for(int i =0; i<edgesOfVertices.length;i++) {
			System.out.print(i + " : ");
			for(int j =0; j<edgesOfVertices[i].length; j++) {
				System.out.print(edgesOfVertices[i][j]);
			}
		}
		
	}

	private static int[][] buildgraph() throws FileNotFoundException {
		
		Scanner data = new Scanner(new File("data/graph.dat"));
		int vertices = Integer.parseInt(data.nextLine());
		int edges = Integer.parseInt(data.nextLine());
		
		int[][] edgesOfVertices = new int[vertices][2];
		
		String[] tempEdges = new String[2];
		String temp;
		for(int i =0; i<vertices; i++) {
			temp = data.nextLine();
			tempEdges = temp.split(" ");
			int num1 = Integer.parseInt(tempEdges[0]);
			int num2 = Integer.parseInt(tempEdges[1]);
			if(edgesOfVertices[num1].length == -1) {
		
				edgesOfVertices[num1][0] = num2;
			}else if(edgesOfVertices[num2].length == -1) {
				edgesOfVertices[num2][1] = num1;
			}
		
		
		}
		return edgesOfVertices;
		
		

		
	
		
				
		
	}
	
	
	

}
