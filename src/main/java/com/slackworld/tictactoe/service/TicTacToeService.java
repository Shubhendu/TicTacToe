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
 * Service class to validate and process request
 * 
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

	/**
	 * Service method to validate and process request coming from Slack Client.
	 * 
	 * @param SlackTicTacToeRequest
	 * @return SlackTicTacToeResponse
	 * @throws Exception
	 */
	public SlackTicTacToeResponse validateAndProcessRequest(SlackTicTacToeRequest request) throws Exception {
		SlackTicTacToeResponse response = validateRequest(request);
		if (response != null) {
			return response;
		}

		if (StringUtils.isEmpty(request.getText())) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, Constant.INVALID_COMMAND, null);
		}

		String[] commands = request.getText().split(Constant.PLAYER_MOVE_SEPARATOR);
		String command = commands[0];
		if (StringUtils.isEmpty(command)) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, Constant.INVALID_COMMAND, null);
		}

		return processCommand(request, command);
	}

	/**
	 * Private helper method to validate command passed to this method.
	 * 
	 * @param request
	 * @return SlackTicTacToeResponse if validation fails else returns null
	 */
	private SlackTicTacToeResponse validateRequest(SlackTicTacToeRequest request) {
		String requestToken = request.getToken();
		String SLACK_TTT_TOKEN = environment.getProperty(Constant.SLACK_TTT_COMMAND_TOKEN);

		if (StringUtils.isEmpty(SLACK_TTT_TOKEN)) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral,
					"Failed to read channel's token value from environment variable", null);
		}

		if (!SLACK_TTT_TOKEN.equals(requestToken)) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, "Invalid token provided", null);
		}

		return null;
	}

	/**
	 * Private helper method to process command passed to this method.
	 * 
	 * @param SlackTicTacToeRequest
	 * @param String
	 * @return SlackTicTacToeResponse
	 */
	private SlackTicTacToeResponse processCommand(SlackTicTacToeRequest request, String command) {
		switch (command.toLowerCase()) {
		case "start":
			return startGameRequestProcessor.process(request);
		case "move":
			return moveRequestProcessor.process(request);
		case "status":
			return gameStatusRequestProcessor.process(request);
		case "help":
			return helpRequestProcessor.process(request);
		case "end":
			return endGameRequestProcessor.process(request);
		default:
			return new SlackTicTacToeResponse(ResponseType.ephemeral, Constant.INVALID_COMMAND, null);
		}
	}
}
