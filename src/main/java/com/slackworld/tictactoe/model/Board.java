package com.slackworld.tictactoe.model;

/**
 * Model to store tic tac toe board
 * @author SSingh
 *
 */
public class Board {
	private int[] rows;
	private int[] cols;
	private int[][] marked;
	private int diagonal;
	private int antiDiagonal;
	private int size;

	public Board(int size) {
		this.size = size;
		this.rows = new int[size];
		this.cols = new int[size];
		this.setMarked(new int[size][size]);
	}

	public int[] getRows() {
		return rows;
	}

	public void setRows(int[] rows) {
		this.rows = rows;
	}

	public int[] getCols() {
		return cols;
	}

	public void setCols(int[] cols) {
		this.cols = cols;
	}

	public int getDiagonal() {
		return diagonal;
	}

	public void setDiagonal(int diagonal) {
		this.diagonal = diagonal;
	}

	public int getAntiDiagonal() {
		return antiDiagonal;
	}

	public void setAntiDiagonal(int antiDiagonal) {
		this.antiDiagonal = antiDiagonal;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int[][] getMarked() {
		return marked;
	}

	public void setMarked(int[][] marked) {
		this.marked = marked;
	}

}
