package com.slackworld.tictactoe.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.enums.ResponseType;
import com.slackworld.tictactoe.model.Game;
import com.slackworld.tictactoe.repository.GameRepository;
import com.slackworld.tictactoe.util.BoardUtil;
import com.slackworld.tictactoe.util.Constant;

/**
 * Processor to provide status of current game to the clients.
 * @author SSingh
 *
 */
@Service
public class GameStatusRequestProcessor implements RequestProcessor {

	@Autowired
	private GameRepository gameRepository;

	@Override
	public SlackTicTacToeResponse process(SlackTicTacToeRequest request) {
		if (!gameRepository.isAnyGameActiveInChannel(request.getChannelId())) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, Constant.NO_GAME_IN_PROGRESS, null);
		}
		Game currentGameInChannel = gameRepository.getGameByChannelId(request.getChannelId());
		return new SlackTicTacToeResponse(ResponseType.ephemeral, getGameStatusMessage(currentGameInChannel), null);
	}

	private String getGameStatusMessage(Game game) {
		StringBuilder sb = new StringBuilder();
		sb.append("Current Game Status: " + game.getStatus());
		sb.append("\n Player 1: " + game.getPlayer1().getSlackUserName());
		sb.append("\n Player 2: " + game.getPlayer2().getSlackUserName());
		sb.append(BoardUtil.drawCurrentBoardInGame(game));
		return sb.toString();
	}

}
