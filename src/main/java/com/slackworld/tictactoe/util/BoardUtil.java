package com.slackworld.tictactoe.util;

import com.slackworld.tictactoe.model.Game;

public class BoardUtil {

	public static String drawCurrentBoardInGame(Game game) {
		StringBuilder currentBoard = new StringBuilder();

		int[][] marked = game.getBoard().getMarked();

		for (int i = 0; i < marked.length; i++) {
			currentBoard.append("\n");
			for (int j = 0; j < marked[0].length; j++) {
				currentBoard.append(" | ");

				if (marked[i][j] == 0) {
					currentBoard.append(":grey_question:");
				} else if (marked[i][j] == 1) {
					currentBoard.append(":x:");
				} else {
					currentBoard.append(":large_blue_circle:");
				}
			}
			currentBoard.append(" | ");
		}
		currentBoard.append("");
		return currentBoard.toString();
	}

	public static String getHelpText() {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"/ttt start [userName] [boardSize] - Challenge a user to play a new game with default board size 3 X 3\n")
				.append("/ttt status - Get the current status of the game played in the channel.\n")
				.append("/ttt move [row] [column] - Play your next move. The row and column index starts with 1.\n")
				.append("/ttt end - End the current game\n").append("/ttt help - Help");
		sb.append("\nNote - All commands are case insensitive");
		return sb.toString();
	}
}
