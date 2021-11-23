package com.demo.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.demo.constant.Constants;
import com.demo.constant.UserStatus;
import com.demo.customer.service.CustomerService;
import com.demo.entity.Customer;
import com.demo.mapper.customer.CustomerMapper;
import com.demo.request.CustomerRequest;
import com.demo.request.MailBody;
import com.demo.response.GetCustomerResponse;
import com.demo.response.SuccessResponse;
import com.demo.utils.AmazonSesUtil;
import com.demo.response.LoginResponse;
import com.demo.utils.PasswordEncryptionArgon2;
import com.demo.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private AmazonSesUtil sesUtil;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ResponseEntity<Object> saveCustomer(CustomerRequest custRequest) {
		log.info("Inside of the CustomerServiceImpl :: saveCustomer method");

		// Create company request DTO to customer object
		Customer createdCustomer = modelMapper.map(custRequest, Customer.class);

		// This method is used to check requested customer name is already exist or not
		if (customerMapper.duplicateEmail(createdCustomer)) { 
			throw new ResponseStatusException(HttpStatus.CONFLICT, Constants.CUSTOMER_ALREADY_IN_USE); 
		}
		
		String encryptPass = PasswordEncryptionArgon2.generateArgon2idToHex(custRequest.getPassword());
		createdCustomer.setPassword(encryptPass);
		
		// Send Email
		try {
			Map<String, Object> properties = new HashMap<>();
//			sesUtil.sendMail(new MailBody(createdCustomer.getEmail(), "subject", "template", properties));
			sesUtil.sendEmail(new MailBody("sangyoub.lee@cnspartner.com", "Test", "test", properties));
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.MAIL_FAILED, exception);
		}
		
		createdCustomer.setStatus(UserStatus.NOT_CONFIRMED);
		customerMapper.saveCustomer(createdCustomer);
		
		log.info("End of the CustomerServiceImpl :: saveCustomer method");
		return ResponseHandler.generateResponse(Constants.CUSTOMER_CREATED, HttpStatus.CREATED, null);
	}
	
	@Override
	public ResponseEntity<Object> updateCustomer(CustomerRequest custRequest) {
		log.info("Inside of the CustomerServiceImpl :: updateCustomer method");
		
		Customer createdCustomer = modelMapper.map(custRequest, Customer.class);
		
		// This method is used to check requested customer name is already exist or not
		if (!customerMapper.duplicateEmail(createdCustomer)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.CUSTOMER_NOT_FOUND);
		}
		
		// Create company request DTO to customer object
		customerMapper.updateCustomer(createdCustomer);
		log.info("End of the CustomerServiceImpl :: updateCustomer method");
		return ResponseHandler.generateResponse(Constants.CUSTOMER_UPDATED, HttpStatus.OK, null);
	}
	
	@Override
	public ResponseEntity<Object> retrieveAll() {
		log.info("Inside of the CustomerServiceImpl :: retrieveAll method");
		
		List<Customer> customers = customerMapper.retrieveAll();
				
		log.info("End of the CustomerServiceImpl :: retrieveAll method");
		return ResponseHandler.generateResponse("Success", HttpStatus.OK, customers);
	}
	
	@Override
	public ResponseEntity<Object> retrieveByEmail(String email, String storeName) {
		log.info("Inside of the CustomerServiceImpl :: retrieveByEmail method");
		
		Customer customer = customerMapper.retrieveByEmail(email, storeName);
				
		if (customer == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.CUSTOMER_NOT_FOUND);
		}
		
		log.info("End of the CustomerServiceImpl :: retrieveByEmail method");
		return ResponseHandler.generateResponse("Success", HttpStatus.OK, modelMapper.map(customer, GetCustomerResponse.class));
	}
	
	@Override
	public ResponseEntity<Object> duplicateEmail(Customer customer) {
		log.info("Inside of the CustomerServiceImpl :: duplicateEmail method");
		
		log.info("End of the CustomerServiceImpl :: duplicateEmail method");
		return ResponseHandler.generateResponse(Constants.CUSTOMER_UPDATED, HttpStatus.OK, customerMapper.duplicateEmail(customer));
	}
	
}
