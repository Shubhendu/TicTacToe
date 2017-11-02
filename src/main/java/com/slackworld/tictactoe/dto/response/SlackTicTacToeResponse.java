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
	private ResponseType responseType;
	private String text;
	private List<Attachment> attachments;

	public SlackTicTacToeResponse() {

	}

	public SlackTicTacToeResponse(ResponseType responseType, String text, List<Attachment> attachments) {
		super();
		this.responseType = responseType;
		this.text = text;
		this.attachments = attachments;
	}

	public ResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
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
