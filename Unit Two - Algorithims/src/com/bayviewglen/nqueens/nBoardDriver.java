package com.bayviewglen.nqueens;

import java.util.Scanner;
import java.util.Stack;

public class nBoardDriver {


	
	public static void main(String[] args) {
		
		introMessage();
		int n = getN();
		Board gameBoard = new Board(n);
		arrangeQueens(n, gameBoard);
		
		
		
		
		
		
		
		
	}
	
	private static void arrangeQueens(int n, Board gameBoard) {
		Stack<Queen> temp = gameBoard.getStack();
		
	}

	/*private static void arrangeQueens(int n,Board gameBoard) {
		Queen queen = new Queen();
		int filled = 0; //want filled to == n
		while(filled < n) {
			if(!conflict(n)) {
				gameBoard.pushToStack(queen);
				filled ++;
				//filled ++, push queen
			}else if(conflict(n) && queen.getCol()<n) {
				queen.setCol(queen.getCol()+1);
			}else {
				Queen temp = gameBoard.popFromStack();
				temp.setCol(temp.getCol()+1);
				if(temp.getCol()>=n) {
					temp = gameBoard.popFromStack();
				}
				temp.setCol(temp.getCol()+1);
			}
		}
		
	}
*/
	private static boolean conflict(int n) {
		
		/*if(conflicts with col){
			return true;
		else if(conflicts w/ diagonals){
			return true;
		}else{
			return false;
		}
		 */
		return false;
	}
		
	private static int getN() {
		Scanner keyboard = new Scanner(System.in);
		boolean valid = false;
		int n;
		while(!valid) {
			System.out.println("Please enter number 'n' which will be the length and width of the graph:");
			try{
				n = Integer.parseInt(keyboard.nextLine());
				valid = true;
				return n;
			}catch(NumberFormatException e){
				System.out.println("That is not a valid number");
			}
		}
		return 0;
	}

	private static void introMessage() {
		System.out.println("Welcome to nQueens");
		System.out.println("This programs purpose is to arrange a Chess board with 'n' number of Queens.");
		System.out.println("After executed, the Queens will be placed in order to be 'out of range' of other Queens.\n");
	}


}
