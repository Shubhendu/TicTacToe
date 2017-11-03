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
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.enums.ResponseType;
import com.slackworld.tictactoe.service.TicTacToeService;
import com.slackworld.tictactoe.util.Constant;
import com.slackworld.tictactoe.util.RequestUtil;

/**
 * Rest controller for this application
 * @author SSingh
 *
 */
@Controller
public class SlackTicTacToeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SlackTicTacToeController.class);

	@Autowired
	private TicTacToeService service;
	
	/**
	 * Health check API used by AWS for health check monitoring.
	 * @return String
	 */
	@RequestMapping(value = { "/", "/health-check" }, method = RequestMethod.GET)
	@ResponseBody
	public String getMainPage() {
		return "The awesome world of Tic Tac Toe is up and running !!";
	}
	
	/**
	 * POST API to play the game of tic tac toe from Slack
	 * @param HttpServletRequest
	 * @return SlackTicTacToeResponse
	 */
	@RequestMapping(value = "/play", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public SlackTicTacToeResponse play(HttpServletRequest request) {
		try {
			SlackTicTacToeRequest slackRequest = RequestUtil.buildRequest(request);
			return service.validateAndProcessRequest(slackRequest);
		} catch (Exception e) {
			LOGGER.error("Something went wrong", e);
			return new SlackTicTacToeResponse(ResponseType.ephemeral, Constant.GENERIC_ERROR_MESSAGE, null);
		}
	}

}
