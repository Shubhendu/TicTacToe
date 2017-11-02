package com.slackworld.tictactoe.dto.response;

import java.util.ArrayList;
import java.util.List;

public class Attachment {

	private String title;
	private String color;
	private List<String> mrkdwn_in;
	private String pretext;
	private String text;
	private String attachment_type;

	public Attachment() {
		// Default settings
		this.color = "#2f56a4";
		this.mrkdwn_in = new ArrayList<String>();
		this.mrkdwn_in.add("text");
		this.mrkdwn_in.add("pretext");
	}
	
	public String getPretext() {
		return pretext;
	}

	public void setPretext(String pretext) {
		this.pretext = pretext;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAttachment_type() {
		return attachment_type;
	}

	public void setAttachment_type(String attachment_type) {
		this.attachment_type = attachment_type;
	}

	public void addMarkdown(String markdown) {
		this.mrkdwn_in.add(markdown);
	}

	public List<String> getMrkdwn_in() {
		return mrkdwn_in;
	}

	public void setMrkdwn_in(List<String> mrkdwn_in) {
		this.mrkdwn_in = mrkdwn_in;
	}

}
