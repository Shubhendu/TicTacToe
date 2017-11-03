package com.slackworld.tictactoe.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.slackworld.tictactoe.dto.request.SlackTicTacToeRequest;
import com.slackworld.tictactoe.dto.response.SlackTicTacToeResponse;
import com.slackworld.tictactoe.enums.ResponseType;
import com.slackworld.tictactoe.service.TicTacToeService;
import com.slackworld.tictactoe.util.ConstantForTests;
import com.slackworld.tictactoe.util.RequestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SlackTicTacToeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private SlackTicTacToeController slackTicTacToeController = new SlackTicTacToeController();

	@Mock
	private Environment environment;

	@Mock
	private TicTacToeService ticTacToeService;;

	private MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
	private SlackTicTacToeRequest request;
	private SlackTicTacToeResponse response;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockHttpServletRequest.addParameter("text", ConstantForTests.DUMMY_TEXT);
		request = RequestUtil.buildRequest(mockHttpServletRequest);
		response = new SlackTicTacToeResponse(ResponseType.ephemeral, "Game started", null);
	}

	@Test
	public void healthCheckReturnMessageFromService() throws Exception {
		this.mockMvc.perform(get("/health-check")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("The awesome world of Tic Tac Toe is up and running !!"));
	}

	@Test
	public void testGameStart() throws Exception {
		when(environment.getProperty("SLACK_TTT_TOKEN")).thenReturn("123445");
		when(ticTacToeService.validateAndProcessRequest(request)).thenReturn(response);
		this.mockMvc.perform(post("/play", mockHttpServletRequest)).andDo(print()).andExpect(status().isOk());
	}

}
