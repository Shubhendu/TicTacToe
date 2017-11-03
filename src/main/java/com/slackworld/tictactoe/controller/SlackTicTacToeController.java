/**
 * 
 */
package com.slackworld.tictactoe.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.Attachment;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.enums.ResponseType;
import com.slackworld.tictactoe.service.TicTacToeService;
import com.slackworld.tictactoe.util.Constant;

/**
 * @author SSingh
 *
 */
@Controller
public class SlackTicTacToeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SlackTicTacToeController.class);

	@Autowired
	private TicTacToeService service;

	@RequestMapping(value = "/health-check", method = RequestMethod.GET)
	@ResponseBody
	public String getMainPage() {
		return "The awesome world of Tic Tac Toe is up and running !!";
	}

	@RequestMapping(value = "/static-response", method = RequestMethod.GET)
	@ResponseBody
	public SlackTicTacToeResponse getSlack() {
		return buildResponse();

	}

	private SlackTicTacToeResponse buildResponse() {
		SlackTicTacToeResponse response = new SlackTicTacToeResponse();
		response.setResponse_type(ResponseType.in_channel);
		response.setText("Let's have some fun !!");

		Attachment attachment = new Attachment();

		attachment.setText(
				"/ttt start [userName] [boardSize] - Challenge a user to play a new game with default board size 3 X 3\n"
						+ "/ttt status - Get the current status of the current game\n"
						+ "/ttt move [row] [column] - Play your next move (the row and column index starts with 1)\n"
						+ "/ttt end - End the current game\n" + "/ttt help - Help");
		System.out.println(attachment.getText());
		response.addAttachment(attachment);
		return response;

	}

	private SlackTicTacToeRequest buildRequest(HttpServletRequest request) {
		SlackTicTacToeRequest slackRequest = new SlackTicTacToeRequest();
		slackRequest.setToken(request.getParameter("token"));
		slackRequest.setChannelId(request.getParameter("channel_id"));
		slackRequest.setUserId(request.getParameter("user_id"));
		slackRequest.setUserName("@" + request.getParameter("user_name"));
		slackRequest.setCommand(request.getParameter("command"));
		slackRequest.setText(request.getParameter("text") != null ? request.getParameter("text").trim() : null);
		slackRequest.setResponseUrl(request.getParameter("response_url"));

		return slackRequest;
	}

	@RequestMapping(value = "/play", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public SlackTicTacToeResponse play(HttpServletRequest request) {
		try {
			SlackTicTacToeRequest slackRequest = buildRequest(request);
			return service.validateAndProcessRequest(slackRequest);
		} catch (Exception e) {
			LOGGER.error("Something went wrong", e);
			return new SlackTicTacToeResponse(ResponseType.ephemeral, Constant.GENERIC_ERROR_MESSAGE, null);
		}
	}

}
