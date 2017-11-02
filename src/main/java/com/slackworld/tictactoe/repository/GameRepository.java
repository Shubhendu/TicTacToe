package com.slackworld.tictactoe.repository;

import com.slackworld.tictactoe.model.Game;

public interface GameRepository {

	public Game getGameByChannelId(String channelId);

	public boolean isAnyGameActiveInChannel(String channelId);

	public void startGameInChannel(String channelId, Game game);
	
	public void endGameInChannel(String channelId);
}
