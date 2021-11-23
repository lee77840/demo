package com.demo.example.resttemplate.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.entity.Customer;
import com.demo.utils.ResttemplateApi;


@Service
public class ResttemplateService<T> {
	
	@Autowired
	private ResttemplateApi<Customer> api;
	
	public ResponseEntity<Customer> findByEmail() {
		
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		ResponseEntity<Customer> customer = api.get("/jpatest/rest/test1@test.com", headers, Customer.class);
		
		return customer;
	}

}
