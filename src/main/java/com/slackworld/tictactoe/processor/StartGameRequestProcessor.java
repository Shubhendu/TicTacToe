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

/**
 * Processor to start the game of tic tac toe.
 * @author ssingh
 *
 */
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

		String[] commands = request.getText().split(Constant.PLAYER_MOVE_SEPARATOR);

		if (commands.length < 2) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "The game requires 2 players to play.", null);
		}

		String[] userDetails = commands[1].split(Pattern.quote("|"));
		if (userDetails.length < 2) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "Invalid user details.", null);
		}
		String opponentUserName = userDetails[0].substring(1, 2)
				+ userDetails[1].substring(0, userDetails[1].length() - 1);
		String opponentUserId = userDetails[0].substring(2);

		if (request.getUserName().equals(opponentUserName)) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "You need 2 different players to play this game.",
					null);
		}

		int boardSize = 3;
		if (commands.length == 3) {
			try {
				boardSize = Integer.valueOf(commands[2]);
			} catch (NumberFormatException e) {
				return new SlackTicTacToeResponse(ResponseType.ephemeral, "Invalid board size.", null);
			}
		}

		if (boardSize < 3) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral,
					"This game needs to be played at least in :three: :negative_squared_cross_mark: :three: board size.",
					null);
		}

		if (boardSize > 6) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral,
					"This game is in beta version and currently we only support max :six: :negative_squared_cross_mark: :six: board size.",
					null);
		}
		List<String> channelUsers = slackClient.getChannelUsers(request.getChannelId());

		if (channelUsers == null || channelUsers.isEmpty()) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral,
					"Unable to fetch user information for this channel.", null);
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
				":new: game of Tic Tac Toe has started between " + p1.getSlackUserName() + " :crossed_swords: "
						+ p2.getSlackUserName() + " on a board of size " + boardSize + ":negative_squared_cross_mark: "
						+ boardSize,
				null);
	}

}
