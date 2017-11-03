package com.slackworld.tictactoe.processor;

import org.springframework.stereotype.Service;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.Attachment;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.enums.ResponseType;

@Service
public class HelpRequestProcessor implements RequestProcessor {

	@Override
	public SlackTicTacToeResponse process(SlackTicTacToeRequest request) {
		return buildHelpResponse();
	}

	private SlackTicTacToeResponse buildHelpResponse() {
		SlackTicTacToeResponse response = new SlackTicTacToeResponse();
		response.setResponse_type(ResponseType.ephemeral);
		response.setText("Let's have some fun !!");

		Attachment attachment = new Attachment();
		attachment.setText(getHelpText());
		response.addAttachment(attachment);

		return response;
	}

	private String getHelpText() {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"/ttt start [userName] [boardSize] - Challenge a user to play a new game with default board size 3 X 3\n")
				.append("/ttt status - Get the current status of the game played in the channel.\n")
				.append("/ttt move [row] [column] - Play your next move. The row and column index starts with 1.\n")
				.append("/ttt end - End the current game\n").append("/ttt help - Help");
		sb.append("\nNote - All commands are case insensitive");
		return sb.toString();
	}
}
