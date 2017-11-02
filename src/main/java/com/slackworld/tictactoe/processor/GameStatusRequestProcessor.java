package com.slackworld.tictactoe.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.enums.ResponseType;
import com.slackworld.tictactoe.model.Game;
import com.slackworld.tictactoe.repository.GameRepository;
import com.slackworld.tictactoe.util.BoardUtil;

@Service
public class GameStatusRequestProcessor implements RequestProcessor {

	@Autowired
	private GameRepository gameRepository;

	@Override
	public SlackTicTacToeResponse process(SlackTicTacToeRequest request) {
		if (!gameRepository.isAnyGameActiveInChannel(request.getChannelId())) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "Sorry, no game in progress", null);
		}
		Game currentGameInChannel = gameRepository.getGameByChannelId(request.getChannelId());
		return new SlackTicTacToeResponse(ResponseType.in_channel, getGameStatusMessage(currentGameInChannel), null);
	}

	private String getGameStatusMessage(Game game) {
		StringBuilder sb = new StringBuilder();
		sb.append("Current Game Staus: " + game.getStatus());
		sb.append(" Player1: " + game.getPlayer1().getUserName());
		sb.append(" Player2: " + game.getPlayer2().getUserName());
		sb.append(BoardUtil.drawCurrentBoardInGame(game));
		return sb.toString();
	}

}
