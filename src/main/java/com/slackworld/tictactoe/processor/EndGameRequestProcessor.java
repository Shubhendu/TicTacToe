package com.slackworld.tictactoe.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.enums.ResponseType;
import com.slackworld.tictactoe.model.Game;
import com.slackworld.tictactoe.model.Player;
import com.slackworld.tictactoe.repository.GameRepository;
import com.slackworld.tictactoe.util.Constant;

@Service
public class EndGameRequestProcessor implements RequestProcessor {

	@Autowired
	private GameRepository gameRepository;

	@Override
	public SlackTicTacToeResponse process(SlackTicTacToeRequest request) {
		if (!gameRepository.isAnyGameActiveInChannel(request.getChannelId())) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, Constant.NO_GAME_IN_PROGRESS, null);
		}
		Game game = gameRepository.getGameByChannelId(request.getChannelId());
		Player requestingPlayer = getPlayerRequestingThisMove(request, game);
		if (requestingPlayer == null) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "Sorry you are not authorized to end this game. :no_entry_sign: ",
					null);
		}
		gameRepository.endGameInChannel(request.getChannelId());
		return new SlackTicTacToeResponse(ResponseType.in_channel,
				"Current game has been :ab:orted by " + requestingPlayer.getSlackUserName(), null);
	}

	private Player getPlayerRequestingThisMove(SlackTicTacToeRequest request, Game game) {
		if (request.getUserName().equals(game.getPlayer1().getUserName())) {
			return game.getPlayer1();
		} else if (request.getUserName().equals(game.getPlayer2().getUserName())) {
			return game.getPlayer2();
		} else {
			return null;
		}
	}

}
