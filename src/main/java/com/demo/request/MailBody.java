package com.demo.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class MailBody {

	private String recipient;
	private String subject;
	private String template;
	private Map<String, Object> props;

	public MailBody(String recipient, String subject, String template, Map<String, Object> props) {
		this.recipient = recipient;
		this.subject = subject;
		this.template = template;
		this.props = props;
	}
}
