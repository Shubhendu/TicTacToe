package com.slackworld.tictactoe.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.slackworld.tictactoe.model.Game;

@Repository
public class GameRepositoryImpl implements GameRepository {
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
