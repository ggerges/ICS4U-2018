package com.bayviewglen.nqueens;

public class Queen {

	private int row;
	private int col;
	
	public Queen() {
		row = 0;
		col = 0;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int newRow) {
		this.row = newRow;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int newCol) {
		this.col = newCol;
	}

	public Queen(int newRow, int newCol) {
		super();
		this.row = newRow;
		this.col = newCol;
	}
	

}
