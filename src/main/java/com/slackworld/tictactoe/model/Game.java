package com.slackworld.tictactoe.model;

import com.slackworld.tictactoe.enums.GameStatus;

public class Game {
	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private Channel channel;
	private Board board;
	private GameStatus status;
	private int moveCount;

	public Game() {
		this.board = new Board(3);
		this.status = GameStatus.NOT_STARTED;
	}

	public Game(int boardSize, Player p1, Player p2) {
		this.board = new Board(boardSize);
		this.player1 = p1;
		this.player2 = p2;
		this.status = GameStatus.IN_PROGRESS;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Player getNextPlayer() {
		return getCurrentPlayer().equals(player1) ? player2 : player1;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public String getGameStatusMessage() {
		return null;
	}

	public boolean isBoardFull() {
		return this.moveCount == board.getSize() * board.getSize();
	}

	public boolean hasAnyPlayerWonGame(int row, int col, Player player) {
		if (Math.abs(this.board.getRows()[row]) == this.board.getSize()
				|| Math.abs(this.board.getCols()[col]) == this.board.getSize()
				|| Math.abs(this.board.getDiagonal()) == this.board.getSize()
				|| Math.abs(this.board.getAntiDiagonal()) == this.board.getSize()) {
			return true;
		}
		return false;
	}

	public void move(int row, int col, Player player) throws Exception {
		if (row < 1 || row > this.board.getSize()) {
			throw new Exception("Invalid row number.");
		}
		if (col < 1 || col > this.board.getSize()) {
			throw new Exception("Invalid column number.");
		}
		row--;
		col--;
		if (isBoardFull()) {
			throw new Exception("This game is already over.");
		}

		if (this.board.getMarked()[row][col] != 0) {
			throw new Exception("This cell is already marked.");
		}

		if (player == null || (!player.getUserName().equals(player1.getUserName())
				&& !player.getUserName().equals(player2.getUserName())) || player.equals(this.currentPlayer)) {
			throw new Exception("Invalid player");
		}
		this.moveCount++;
		this.setCurrentPlayer(player);
		markMoveOnBoard(row, col, player);
		updateGameStatus(row, col, player);
	}

	private void markMoveOnBoard(int row, int col, Player player) {
		int numToAdd = player.equals(player1) ? 1 : -1;

		int[] rows = this.board.getRows();
		int[] cols = this.board.getCols();
		int[][] marked = this.board.getMarked();
		int diagonal = this.board.getDiagonal();
		int antiDiagonal = this.board.getAntiDiagonal();

		rows[row] += numToAdd;
		cols[col] += numToAdd;

		if (row == col) {
			diagonal += numToAdd;
		}

		if ((row + col) == this.board.getSize() - 1) {
			antiDiagonal += numToAdd;
		}

		marked[row][col] = numToAdd;

		this.board.setRows(rows);
		this.board.setCols(cols);
		this.board.setDiagonal(diagonal);
		this.board.setAntiDiagonal(antiDiagonal);
		this.board.setMarked(marked);

	}

	private void updateGameStatus(int row, int col, Player player) {
		if (hasAnyPlayerWonGame(row, col, player)) {
			if (player.equals(player1)) {
				this.setStatus(GameStatus.PLAYER_1_WON);
			} else {
				this.setStatus(GameStatus.PLAYER_2_WON);
			}
		} else if (isBoardFull()) {
			this.setStatus(GameStatus.DRAW);
		} else {
			this.setStatus(GameStatus.IN_PROGRESS);
		}
	}
}
