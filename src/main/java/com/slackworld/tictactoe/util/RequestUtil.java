/**
 * 
 */
package com.slackworld.tictactoe.util;

import javax.servlet.http.HttpServletRequest;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;

/**
 * @author SSingh
 *
 */
public class RequestUtil {
	public static SlackTicTacToeRequest buildRequest(HttpServletRequest request) {
		SlackTicTacToeRequest slackRequest = new SlackTicTacToeRequest();
		if (request == null) {
			return slackRequest;
		}
		slackRequest.setToken(request.getParameter("token"));
		slackRequest.setChannelId(request.getParameter("channel_id"));
		slackRequest.setUserId(request.getParameter("user_id"));
		slackRequest.setUserName("@" + request.getParameter("user_name"));
		slackRequest.setCommand(request.getParameter("command"));
		slackRequest.setText(request.getParameter("text") != null ? request.getParameter("text").trim() : null);
		slackRequest.setResponseUrl(request.getParameter("response_url"));
		return slackRequest;
	}
}
