/**
 * 
 */
package com.slackworld.tictactoe.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.slackworld.tictactoe.enums.ResponseType;

/**
 * @author SSingh
 *
 */
public class SlackTicTacToeResponse {
	private ResponseType response_type;
	private String text;
	private List<Attachment> attachments;

	public SlackTicTacToeResponse() {

	}

	public SlackTicTacToeResponse(ResponseType response_type, String text, List<Attachment> attachments) {
		super();
		this.response_type = response_type;
		this.text = text;
		this.attachments = attachments;
	}

	public ResponseType getResponse_type() {
		return response_type;
	}

	public void setResponse_type(ResponseType response_type) {
		this.response_type = response_type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public void addAttachment(Attachment attachment) {
		if (this.attachments == null) {
			this.attachments = new ArrayList<Attachment>();
		}
		this.attachments.add(attachment);
	}

}
