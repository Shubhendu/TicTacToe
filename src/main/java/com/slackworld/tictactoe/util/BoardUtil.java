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
}
