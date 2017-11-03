package com.slackworld.tictactoe.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.enums.GameStatus;
import com.slackworld.tictactoe.enums.ResponseType;
import com.slackworld.tictactoe.model.Game;
import com.slackworld.tictactoe.model.Player;
import com.slackworld.tictactoe.repository.GameRepository;
import com.slackworld.tictactoe.util.BoardUtil;
import com.slackworld.tictactoe.util.Constant;

@Service
public class MoveRequestProcessor implements RequestProcessor {

	@Autowired
	private GameRepository gameRepository;

	@Override
	public SlackTicTacToeResponse process(SlackTicTacToeRequest request) {
		return markBoard(request);
	}

	private SlackTicTacToeResponse markBoard(SlackTicTacToeRequest request) {
		if (!gameRepository.isAnyGameActiveInChannel(request.getChannelId())) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, Constant.NO_GAME_IN_PROGRESS, null);
		}
		Game game = gameRepository.getGameByChannelId(request.getChannelId());

		String commandText = request.getText().trim();
		String[] commands = commandText.split(Constant.PLAYER_MOVE_SEPARATOR);

		if (commands.length < 3) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, Constant.INVALID_COMMAND, null);
		}

		if (!isPlayersTurn(request, game)) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, game.getNextPlayer().getSlackUserName(), null);
		}

		int row = 0;
		int col = 0;

		try {
			row = Integer.valueOf(commands[1]);
			col = Integer.valueOf(commands[2]);
		} catch (NumberFormatException e) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "Invalid request", null);
		}

		Player currentPlayer = new Player(request.getUserId(), request.getUserName());

		try {
			game.move(row, col, currentPlayer);
			if (!GameStatus.IN_PROGRESS.equals(game.getStatus())) {
				switch (game.getStatus()) {
				case PLAYER_1_WON:
					return new SlackTicTacToeResponse(ResponseType.in_channel,
							buildGameOverMessage(request.getChannelId(), game, game.getPlayer1()), null);
				case PLAYER_2_WON:
					return new SlackTicTacToeResponse(ResponseType.in_channel,
							buildGameOverMessage(request.getChannelId(), game, game.getPlayer2()), null);
				default:
					return new SlackTicTacToeResponse(ResponseType.in_channel,
							buildGameOverMessage(request.getChannelId(), game, null), null);
				}
			}
			return new SlackTicTacToeResponse(ResponseType.in_channel,
					getGameInProgressMessage(game, request, row, col), null);
		} catch (Exception e) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, e.getMessage(), null);
		}
	}

	private String getGameInProgressMessage(Game game, SlackTicTacToeRequest request, int row, int col) {
		StringBuilder sb = new StringBuilder();
		sb.append(game.getCurrentPlayer().getSlackUserName()).append(" last move was at (").append(row).append(", ")
				.append(col).append(")");

		sb.append(BoardUtil.drawCurrentBoardInGame(game));
		sb.append("\n Your move :game_die: next ").append(game.getNextPlayer().getSlackUserName());
		return sb.toString();
	}

	private String buildGameOverMessage(String channelId, Game game, Player winner) {
		StringBuilder sb = new StringBuilder();
		if (winner != null) {
			sb.append("Congratulations ").append(game.getCurrentPlayer().getSlackUserName())
					.append(" for winning the game !! \n");
			sb.append(BoardUtil.drawCurrentBoardInGame(game));
			sb.append("\n");
			sb.append("\n :tada:");
			sb.append("\t:sparkles:");
			sb.append("\t:fireworks:");
		} else {
			sb.append("Match is drawn");
			sb.append(BoardUtil.drawCurrentBoardInGame(game));
			sb.append("\n");
			sb.append("\n :neutral_face: ");
		}
		// Attachment attachment = new Attachment();
		// attachment.setText(BoardUtil.drawCurrentBoardInGame(game));

		gameRepository.endGameInChannel(channelId);
		return sb.toString();
	}

	private boolean isPlayersTurn(SlackTicTacToeRequest request, Game game) {
		if (game.getCurrentPlayer() != null && request.getUserId().equals(game.getCurrentPlayer().getUserId())) {
			return false;
		}
		return true;
	}
}
