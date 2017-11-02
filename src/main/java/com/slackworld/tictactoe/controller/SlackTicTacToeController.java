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

/**
 * @author SSingh
 *
 */
@Controller
public class SlackTicTacToeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SlackTicTacToeController.class);

	@Autowired
	private TicTacToeService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String getMainPage() {
		return "Hello world !!";
	}

	@RequestMapping(value = "/static-response", method = RequestMethod.GET)
	@ResponseBody
	public SlackTicTacToeResponse getSlack() {
		return buildResponse();

	}

	private SlackTicTacToeResponse buildResponse() {
		SlackTicTacToeResponse response = new SlackTicTacToeResponse();
		response.setResponseType(ResponseType.in_channel);
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
		slackRequest.setText(request.getParameter("text"));
		slackRequest.setResponseUrl(request.getParameter("response_url"));

		return slackRequest;
	}

	@RequestMapping(value = "/play", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public SlackTicTacToeResponse play(HttpServletRequest request) {

		SlackTicTacToeRequest slackRequest = buildRequest(request);

		LOGGER.info(
				"[SLACK_TIC_TAC_API_CALLED]: token : {}, channel_id : {}, user_id : {}, user_name : {}, command : {}, text : {}, response_url : {}",
				slackRequest.getToken(), slackRequest.getChannelId(), slackRequest.getUserId(),
				slackRequest.getUserName(), slackRequest.getCommand(), slackRequest.getText(),
				slackRequest.getResponseUrl());

		return service.validateAndProcessRequest(slackRequest);
	}

}
