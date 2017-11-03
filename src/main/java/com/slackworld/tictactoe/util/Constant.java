package com.slackworld.tictactoe.util;

public class Constant {
	public static final String STATUS_REQUEST_COMMAND = "status";
	public static final String HELP_REQUEST_COMMAND = "help";
	public static final String PLAYER_MOVE_SEPARATOR = " ";
	public static final String USAGE_TTT_MARK = "[Usage] /ttt mark row(number) col(number)";
	public static final String SLACK_API_TOKEN = "SLACK_API_TOKEN";
	public static final String SLACK_BASE_URL = "https://slack.com/api/channels.info";
	public static final String SLACK_TTT_COMMAND_TOKEN = "SLACK_TTT_TOKEN";
	public static final String NO_GAME_IN_PROGRESS = "No game in progress in this channel.";
	public static final String INVALID_COMMAND = "Invalid command. /n Please use /ttt help to check the correct usage.";
	public static final String GENERIC_ERROR_MESSAGE = "We are extremely sorry but this was not expected.\nPlease contact system administrator.\n :disappointed:";
}
