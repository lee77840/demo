package com.demo.mapper.customer;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.demo.entity.Customer;

@Mapper
public interface CustomerMapper {

	public int saveCustomer(Customer customer);
	
	public int updateCustomer(Customer customer);
	
	public List<Customer> retrieveAll();
	
	public boolean duplicateEmail(Customer customer);

	public Customer retrieveByEmail(String email, String storeName);
	
}

