package com.bayviewglen.nqueens;

import java.util.Stack;

public class Board {
	private Stack<Queen> queens = new Stack<Queen>();
	private int filled;
	private Queen[][] Board;
	private int n;
	
	public Board() {
		n = 1;
		filled = 0;
		Board = new Queen[n][n];
		
	}

	public Board(int n) {
		super();
		this.n = n;
		filled = 0;
		Board = new Queen[n][n];
	}

	public Queen[][] getBoard() {
		return Board;
	}

	public void setBoard(Queen[][] board) {
		Board = board;
	}
	
	public Stack getStack() {
		return queens;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}
	public void pushToStack(Queen x) {
		queens.push(x);
		filled ++;
	}
	public Queen popFromStack() {
		filled --;
		return queens.pop();
	}

}
