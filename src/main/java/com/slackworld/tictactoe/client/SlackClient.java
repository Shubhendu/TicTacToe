package com.slackworld.tictactoe.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.slackworld.tictactoe.util.Constant;

@Service
public class SlackClient {
	class SlackApiResponse {
		String ok;
		Channel channel;
	}

	class Channel {
		String id;
		String name;
		List<String> members;
	}

	@Autowired
	private Environment environment;

	private String callSlackChannelInfoApi(String channelId) {
		String apiToken = environment.getProperty(Constant.SLACK_API_TOKEN);
		String uri = Constant.SLACK_BASE_URL + "?token=" + apiToken + "&channel=" + channelId;

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, String.class);
	}

	private static List<String> getMembers(String response) {
		Gson gson = new Gson();
		SlackApiResponse r = gson.fromJson(response, SlackApiResponse.class);
		if (r == null) {
			return null;
		}
		return r.channel != null ? r.channel.members : null;
	}

	public List<String> getChannelUsers(String channelId) {
		String response = callSlackChannelInfoApi(channelId);
		if (response == null) {
			return null;
		}
		return getMembers(response);
	}
}
