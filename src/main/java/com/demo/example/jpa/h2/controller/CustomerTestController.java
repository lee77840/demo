package com.demo.example.jpa.h2.controller;

import com.demo.customer.service.impl.CustomerServiceImpl;
import com.demo.entity.Customer;
import com.demo.example.jpa.h2.service.CustomerTestService;
import com.demo.utils.ResttemplateApi;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("jpatest")
public class CustomerTestController {

	@Autowired
	CustomerTestService customerTestService;
	
	@Autowired
	private ResttemplateApi<String> restService;

//	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<List<Customer>> getAllCustomers() {
//		
//		List<Customer> customer = customerService.findAll();
//		
//		return new ResponseEntity<List<Customer>>(customer, HttpStatus.OK);
//	}
//	
//	// select by id
//	@GetMapping(value = "/{email}", produces = { MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<Customer> getCustomer(@PathVariable("email") String email) {
//		
//		Optional<Customer> customer = customerService.findByEmail(email);
//		
//		return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
//	}
	
	// select by id
	@GetMapping(value = "/rest/{email}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Customer> getRestCustomer(@PathVariable("email") String email) {
		
//		Optional<Customer> customer = customerService.findByEmail(email);
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + customer.toString());
//		return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
		
		final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
//		ResponseEntity<Customer> response = restService.get("http://localhost:8083/jpatest/test1@test.com", HttpHeaders.EMPTY, Customer.class);
//        ResponseEntity<String> response = restService.get("https://wdev.lgeonlineshop.com/eos/rest/V1/store/storeName", headers, String.class);
        ResponseEntity<String> response = restService.get("http://f.lg.com:8080/eos/rest/V1/store/storeName", headers, String.class);
		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + response.getBody());
		
		return new ResponseEntity<Customer>(new Customer(), HttpStatus.OK);
	}

}
