package com.slackworld.tictactoe.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.enums.ResponseType;
import com.slackworld.tictactoe.model.Game;
import com.slackworld.tictactoe.repository.GameRepository;

@Service
public class EndGameRequestProcessor implements RequestProcessor {

	@Autowired
	private GameRepository gameRepository;

	@Override
	public SlackTicTacToeResponse process(SlackTicTacToeRequest request) {
		if (!gameRepository.isAnyGameActiveInChannel(request.getChannelId())) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "No game in progress in this channel.", null);
		}
		Game game = gameRepository.getGameByChannelId(request.getChannelId());
		if (!request.getUserName().equals(game.getPlayer1().getUserName())
				&& !request.getUserName().equals(game.getPlayer2().getUserName())) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "Sorry you are not authorized to end this game.",
					null);
		}
		gameRepository.endGameInChannel(request.getChannelId());
		return new SlackTicTacToeResponse(ResponseType.in_channel,
				"Current game has been aborted by the player " + request.getUserName(), null);
	}

}
