package com.demo.example.jpa.h2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entity.Customer;
import com.demo.entity.view.CustomerView;
import com.demo.request.CustomerRequest;
import com.demo.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

public interface CustomerTestService {
	
	/**
     * This method is used to create a new company
     *
     * @param company Company Request DTO
     * @return Success Response
     */
	SuccessResponse save(CustomerRequest customer);
	
	
}
