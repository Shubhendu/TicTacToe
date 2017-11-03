package com.slackworld.tictactoe.processor;

import org.springframework.stereotype.Service;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.Attachment;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.enums.ResponseType;
import com.slackworld.tictactoe.util.BoardUtil;
import com.slackworld.tictactoe.util.Constant;

/**
 * Processor that would provide help message to client.
 * @author ssingh
 *
 */
@Service
public class HelpRequestProcessor implements RequestProcessor {

	@Override
	public SlackTicTacToeResponse process(SlackTicTacToeRequest request) {
		return buildHelpResponse();
	}
	
	/**
	 * Private helper method to build help message for the client.
	 * @return
	 */
	private SlackTicTacToeResponse buildHelpResponse() {
		SlackTicTacToeResponse response = new SlackTicTacToeResponse();
		response.setResponse_type(ResponseType.ephemeral);
		response.setText(Constant.STARTER_MSG);

		Attachment attachment = new Attachment();
		attachment.setText(BoardUtil.getHelpText());
		response.addAttachment(attachment);
		return response;
	}
}
