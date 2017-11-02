/**
 * 
 */
package com.slackworld.tictactoe.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.enums.ResponseType;
import com.slackworld.tictactoe.processor.EndGameRequestProcessor;
import com.slackworld.tictactoe.processor.GameStatusRequestProcessor;
import com.slackworld.tictactoe.processor.HelpRequestProcessor;
import com.slackworld.tictactoe.processor.MoveRequestProcessor;
import com.slackworld.tictactoe.processor.StartGameRequestProcessor;
import com.slackworld.tictactoe.util.Constant;

/**
 * @author SSingh
 *
 */
@Service
public class TicTacToeService {
	
	@Autowired
	private Environment environment;

	@Autowired
	private HelpRequestProcessor helpRequestProcessor;

	@Autowired
	private StartGameRequestProcessor startGameRequestProcessor;

	@Autowired
	private MoveRequestProcessor moveRequestProcessor;

	@Autowired
	private GameStatusRequestProcessor gameStatusRequestProcessor;

	@Autowired
	private EndGameRequestProcessor endGameRequestProcessor;

	private SlackTicTacToeResponse validateRequest(SlackTicTacToeRequest request) {

		final String tokenToValidate = request.getToken();

		final String SLACK_TTT_TOKEN = environment.getProperty(Constant.SLACK_TTT_COMMAND_TOKEN);

		if (StringUtils.isEmpty(SLACK_TTT_TOKEN)) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral,
					"Failed to read channel's token value from environment variable", null);
		}

		if (!SLACK_TTT_TOKEN.equals(tokenToValidate)) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "Invalid token provided", null);
		}

		if (!SLACK_TTT_TOKEN.equals(tokenToValidate)) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "Invalid command", null);
		}

		return null;
	}

	public SlackTicTacToeResponse validateAndProcessRequest(SlackTicTacToeRequest request) {

		SlackTicTacToeResponse response = validateRequest(request);
		if (response != null) {
			return response;
		}

		final String[] commands = request.getText().split(Constant.PLAYER_MOVE_SEPARATOR);
		final String cmd = commands[0];
		switch (cmd) {
		case "start":
			return startGameRequestProcessor.process(request);
		case "mark":
			return moveRequestProcessor.process(request);
		case "status":
			return gameStatusRequestProcessor.process(request);
		case "help":
			return helpRequestProcessor.process(request);
		case "end":
			return endGameRequestProcessor.process(request);
		default:
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "Invalid command", null);
		}
	}
}
