package com.demo.mapper.credential;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.demo.entity.Customer;

@Mapper
public interface CredentialMapper {

	/**
	 * Find customer by email and store
	 * @param email
	 * @param storeName
	 * @return
	 */
	public Customer findByEmailAndStore(@Param("email") String email, @Param("storeName") String storeName);

	/**
	 * Update customer account lock details
	 * @param customer
	 */
	public void updateAccountLock(Customer customer);
	
	/**
	 * Update customer reset password token and created date
	 * @param email
	 * @param storeName
	 * @param rpToken
	 */
	public void savePasswordResetToken(@Param("email") String email, @Param("storeName") String storeName, @Param("rpToken") String rpToken);
	
	/**
	 * Reset new password for given customer
	 * @param email
	 * @param storeName
	 * @param password
	 */
	public void resetPassword(@Param("email") String email, @Param("storeName") String storeName, @Param("password") String password);
}
