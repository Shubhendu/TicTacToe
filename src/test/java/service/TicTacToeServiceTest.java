package service;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.service.TicTacToeService;
import com.slackworld.tictactoe.util.Constant;
import com.slackworld.tictactoe.util.ConstantForTests;

@RunWith(MockitoJUnitRunner.class)
public class TicTacToeServiceTest {
	@InjectMocks
	private TicTacToeService ticTacToeService;

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
		SlackTicTacToeResponse response = ticTacToeService.validateAndProcessRequest(request);
		Assert.assertEquals(response.getText(), Constant.INVALID_COMMAND);
	}
}
