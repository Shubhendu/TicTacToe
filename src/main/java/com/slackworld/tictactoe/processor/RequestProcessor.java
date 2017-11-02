package com.slackworld.tictactoe.processor;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;

public interface RequestProcessor {
	SlackTicTacToeResponse process(SlackTicTacToeRequest request);
}
