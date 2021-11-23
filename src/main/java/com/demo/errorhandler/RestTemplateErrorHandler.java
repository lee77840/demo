package com.demo.errorhandler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		log.error("Please handle Error : " + response.getStatusCode());
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		boolean hasError = false;
		if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
			log.error("RESTAPI Returns 500");
		}
		if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			log.error("RESTAPI Unauthorized");
		}
		if (response.getStatusCode() == HttpStatus.METHOD_NOT_ALLOWED) {
			log.error("RESTAPI Method Not Allowed");
		}
		if (response.getStatusCode() != HttpStatus.OK) {
			return true;
		}
		return hasError;
	}
}
