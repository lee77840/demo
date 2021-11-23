package com.demo.credential.service;

import org.springframework.http.ResponseEntity;

public interface CredentialService {

	/**
	 * This method is used to authenticate user by email and password
	 * @param email
	 * @param password
	 * @param storeName
	 * @return
	 */
	ResponseEntity<Object> authenticate(String email, String password, String storeName);
	
	/**
	 * Initialize password reset process with token generation
	 * @param email
	 * @param storeName
	 * @return
	 */
	ResponseEntity<Object> resetPasswordInit(String email, String storeName);
	
	/**
	 * Reset the given new password
	 * @param email Customer email
	 * @param rpToken Reset password token
	 * @param newPassword New password
	 * @param storeName
	 * @return
	 */
	ResponseEntity<Object> resetPasswordFinish(String email, String rpToken, String password, String storeName);
}
