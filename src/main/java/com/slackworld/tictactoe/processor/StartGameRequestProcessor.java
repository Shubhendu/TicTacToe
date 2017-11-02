package com.slackworld.tictactoe.processor;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slackworld.tictactoe.client.SlackClient;
import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.enums.ResponseType;
import com.slackworld.tictactoe.model.Game;
import com.slackworld.tictactoe.model.Player;
import com.slackworld.tictactoe.repository.GameRepository;
import com.slackworld.tictactoe.util.Constant;

@Service
public class StartGameRequestProcessor implements RequestProcessor {
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private SlackClient slackClient;

	@Override
	public SlackTicTacToeResponse process(SlackTicTacToeRequest request) {
		if (gameRepository.isAnyGameActiveInChannel(request.getChannelId())) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "Active game in progress in this channel.", null);
		}
		// expecting 2nd token for opponent player

		String[] tokens = request.getText().split(Constant.PLAYER_MOVE_SEPARATOR);

		if (tokens.length < 2) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "The game requires 2 players to play", null);
		}
		String[] userDetails = tokens[1].split(Pattern.quote("|"));
		String opponentUserName = userDetails[0].substring(1, 2)
				+ userDetails[1].substring(0, userDetails[1].length() - 1);
		String opponentUserId = userDetails[0].substring(2);

		if (request.getUserName().equals(opponentUserName)) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "You need 2 different players to play this game.",
					null);
		}
		// if 3rd token exists, then need to create a game with bigger than 3x3
		// board.
		int boardSize = 3;
		if (tokens.length == 3) {
			try {
				boardSize = Integer.valueOf(tokens[2]);
			} catch (NumberFormatException e) {
				// suppress
			}
		}

		if (boardSize < 3) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral,
					"This game needs to be played at least in 3 x 3 board size.", null);
		}

		List<String> channelUsers = slackClient.getChannelUsers(request.getChannelId());

		if (channelUsers == null || channelUsers.isEmpty()) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral,
					"Unable to fetch user information from the channel.", null);
		}

		boolean isValidOpponent = channelUsers.stream().anyMatch(channelUser -> opponentUserId.equals(channelUser));
		if (!isValidOpponent) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral,
					"User name " + opponentUserName + " doesn't exist in the given channel.", null);
		}

		Player p1 = new Player(request.getUserId(), request.getUserName());
		Player p2 = new Player(opponentUserId, opponentUserName);

		Game game = new Game(boardSize, p1, p2);
		gameRepository.startGameInChannel(request.getChannelId(), game);
		return new SlackTicTacToeResponse(ResponseType.in_channel,
				"New game has started between " + p1.getUserName() + " and " + p2.getUserName(), null);
	}

}
