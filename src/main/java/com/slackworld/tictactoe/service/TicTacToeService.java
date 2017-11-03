/**
 * 
 */
package com.slackworld.tictactoe.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(TicTacToeService.class);

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

	public SlackTicTacToeResponse validateAndProcessRequest(SlackTicTacToeRequest request) throws Exception {

		LOGGER.info(
				"[SLACK_TIC_TAC_TOE_SERVICE_CALLED]: token : {}, channel_id : {}, user_id : {}, user_name : {}, command : {}, text : {}, response_url : {}",
				request.getToken(), request.getChannelId(), request.getUserId(), request.getUserName(),
				request.getCommand(), request.getText(), request.getResponseUrl());

		SlackTicTacToeResponse response = validateRequest(request);
		if (response != null) {
			return response;
		}
		
		if (StringUtils.isEmpty(request.getText())) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral, Constant.INVALID_COMMAND, null);
		}

		String[] commands = request.getText().split(Constant.PLAYER_MOVE_SEPARATOR);
		String cmd = commands[0];
		if (StringUtils.isEmpty(cmd)) {
			return new SlackTicTacToeResponse(ResponseType.ephemeral,
					Constant.INVALID_COMMAND, null);
		}
		
		switch (cmd.toLowerCase()) {
		case "start":
			return startGameRequestProcessor.process(request);
		case "move":
			return moveRequestProcessor.process(request);
		case "status":
			return gameStatusRequestProcessor.process(request);
		case "help":
			System.out.println("Calling help");
			return helpRequestProcessor.process(request);
		case "end":
			return endGameRequestProcessor.process(request);
		default:
			return new SlackTicTacToeResponse(ResponseType.ephemeral, Constant.INVALID_COMMAND, null);
		}

	}
}
