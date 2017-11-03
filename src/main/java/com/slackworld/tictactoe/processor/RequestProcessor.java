package com.slackworld.tictactoe.processor;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;

/**
 * Any class implementing this Interface will define their processing method. 
 * @author SSingh
 *
 */
public interface RequestProcessor {
	SlackTicTacToeResponse process(SlackTicTacToeRequest request);
}
