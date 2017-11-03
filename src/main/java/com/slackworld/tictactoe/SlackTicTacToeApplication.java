package com.slackworld.tictactoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Main class that kickstarts this application
 * @author ssingh
 *
 */
@SpringBootApplication
public class SlackTicTacToeApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SlackTicTacToeApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SlackTicTacToeApplication.class, args);
	}

}