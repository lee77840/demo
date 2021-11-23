package com.demo.example.jpa.h2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.demo.constant.Constants;
import com.demo.entity.Customer;
import com.demo.entity.view.CustomerView;
import com.demo.example.jpa.h2.service.CustomerTestService;
import com.demo.mapper.customer.CustomerMapper;
import com.demo.request.CustomerRequest;
import com.demo.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@Service
public class CustomerTestServiceImpl implements CustomerTestService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerTestService.class);
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public SuccessResponse save(CustomerRequest custRequest) {
		logger.info("Inside of the CustomerServiceImpl :: save method");
		// Create company request DTO to Company object
		Customer createdCustomer = modelMapper.map(custRequest, Customer.class);
		
		// This method is used to check requested company name is already exist or not
		if (customerMapper.duplicateEmail(createdCustomer)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, Constants.CUSTOMER_ALREADY_IN_USE);
		}
		
		customerMapper.saveCustomer(createdCustomer);
		logger.info("End of the CustomerServiceImpl :: save method");
		return new SuccessResponse(Constants.CUSTOMER_CREATED);
	}

}
