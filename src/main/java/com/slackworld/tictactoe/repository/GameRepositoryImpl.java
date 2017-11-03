package com.slackworld.tictactoe.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.slackworld.tictactoe.model.Game;

/**
 * Repo layer.
 * 
 * @author SSingh
 *
 */
@Repository
public class GameRepositoryImpl implements GameRepository {
	/*
	 * Currently we are using a map to do in memory storage.
	 * This can easily be configured to use something like REDIS or any other data storage.
	 * With any change going in this layer, service clients would not be impacted by any ways.
	 */
	
	private Map<String, Game> gameRepo;

	public GameRepositoryImpl() {
		this.gameRepo = new HashMap<String, Game>();
	}

	@Override
	public Game getGameByChannelId(String channelId) {
		return this.gameRepo.getOrDefault(channelId, null);
	}

	@Override
	public boolean isAnyGameActiveInChannel(String channelId) {
		return this.gameRepo.containsKey(channelId);
	}

	@Override
	public void startGameInChannel(String channelId, Game game) {
		this.gameRepo.put(channelId, game);
	}

	@Override
	public void endGameInChannel(String channelId) {
		this.gameRepo.remove(channelId);

	}

}
