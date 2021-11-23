package com.demo.utils;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.demo.constant.Constants;
import com.demo.request.MailBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AmazonSesUtil {
	
	@Autowired
	private SpringTemplateEngine templateEngine;

	@Autowired
	private JavaMailSender emailSender;
	
	@Async
	public void sendEmail(MailBody mailBody) {
		log.info("Start AmazonSesUtil:sendMail");
		// Init Mail Mime Message
		MimeMessage message = emailSender.createMimeMessage();
		Context context = new Context();
		// Set variables to the context
		context.setVariables(mailBody.getProps());
		// Process and Parsing data to HTML and return html body
		String htmlBody = templateEngine.process(mailBody.getTemplate(), context);
		try {
			// Mime Helper class is used to set mail prerequisites details
			MimeMessageHelper helper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// Attached logo - from resources folder
//			helper.addAttachment("logo.svg", new ClassPathResource("/img/logo.svg"));
			// Set Recipient
			helper.setTo(mailBody.getRecipient());
			// Set HTML Body
			helper.setText(htmlBody.toString(), true);
			// Set Subject
			helper.setSubject(mailBody.getSubject());
			// Set sender
			helper.setFrom(new InternetAddress(Constants.SENDER, "LGE Administrator"));
		} catch (MessagingException | UnsupportedEncodingException exception) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), exception);
		}
		// Send method is used to sending mail to a recipient
		emailSender.send(message);
		log.info("End AmazonSesUtil:sendMail");
	}
	
}
