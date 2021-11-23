package com.demo.customer.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.demo.entity.Customer;
import com.demo.request.CustomerRequest;
import com.demo.response.GetCustomerResponse;
import com.demo.response.SuccessResponse;

public interface CustomerService {

	/**
     * This method is used to create a new customer
     *
     * @param customer Request DTO
     * @return ResponseEntity<Object>
     */
	ResponseEntity<Object> saveCustomer(CustomerRequest customer);
	
	/**
     * This method is used to update customer info
     *
     * @param customer Request DTO
     * @return ResponseEntity<Object>
     */
	ResponseEntity<Object> updateCustomer(CustomerRequest customer);
	
	/**
     * This method is used to retrieve all customers
     *
     * @param 
     * @return ResponseEntity<Object>
     */
	ResponseEntity<Object> retrieveAll();
	
	/**
     * This method is used to retrieve customer by email
     *
     * @param email
     * @return ResponseEntity<Object>
     */
	ResponseEntity<Object> retrieveByEmail(String email, String storeName);

	
	/**
     * This method is used to check duplicate email
     *
     * @param email
     * @return ResponseEntity<Object>
     */
	ResponseEntity<Object> duplicateEmail(Customer customer);
	
}
