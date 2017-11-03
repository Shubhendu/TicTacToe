package com.slackworld.tictactoe.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.slackworld.tictactoe.util.Constant;

/**
 * REST Service class that makes Slack Channel.info API call to get information about users in channel.
 * More info here - https://api.slack.com/methods/channels.info
 * @author ssingh
 *
 */
@Service
public class SlackClient {
	
	@Autowired
	private Environment environment;

	class SlackApiResponse {
		String ok;
		Channel channel;
	}

	class Channel {
		String id;
		String name;
		List<String> members;
	}

	private String buildSlackApiUrl(String channelId) {
		StringBuilder sb = new StringBuilder();
		sb.append(environment.getProperty(Constant.SLACK_API_TOKEN))
		.append(Constant.SLACK_BASE_URL).append("?token=")
		.append(environment.getProperty(Constant.SLACK_API_TOKEN))
		.append("&channel=").append(channelId);
		return sb.toString();

	}

	private String callSlackChannelInfoApi(String channelId) {
		String uri = buildSlackApiUrl(channelId);
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
