package com.slackworld.tictactoe.processor;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.util.BoardUtil;
import com.slackworld.tictactoe.util.Constant;
import com.slackworld.tictactoe.util.ConstantForTests;

public class HelpRequestProcessorTest {
	@InjectMocks
	private HelpRequestProcessor helpRequestProcessor;

	@Mock
	private Environment environment;

	private SlackTicTacToeRequest request;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		request = new SlackTicTacToeRequest();
		request.setToken(ConstantForTests.DUMMY_TOKEN);
		request.setChannelId(ConstantForTests.DUMMY_CHANNEL_ID);
	}

	@Test
	public void testProcessWithInvalidCommand() throws Exception {
		when(environment.getProperty(Constant.SLACK_TTT_COMMAND_TOKEN)).thenReturn(ConstantForTests.DUMMY_TOKEN);
		SlackTicTacToeResponse response = helpRequestProcessor.process(request);
		Assert.assertEquals(response.getText(), Constant.STARTER_MSG);
		Assert.assertEquals(response.getAttachments().get(0).getText(), BoardUtil.getHelpText());
	}
}
