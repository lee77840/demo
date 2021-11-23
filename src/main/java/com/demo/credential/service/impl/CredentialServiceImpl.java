package com.demo.credential.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.demo.constant.Constants;
import com.demo.constant.UserStatus;
import com.demo.credential.service.CredentialService;
import com.demo.entity.Customer;
import com.demo.mapper.credential.CredentialMapper;
import com.demo.request.MailBody;
import com.demo.response.LoginResponse;
import com.demo.utils.AmazonSesUtil;
import com.demo.utils.PasswordEncryptionArgon2;
import com.demo.utils.RandomUtil;
import com.demo.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CredentialServiceImpl implements CredentialService {

	@Value("${accountlock.maxFailedAttempt}")
	private int maxFailedAttampt;

	@Value("${accountlock.lockoutMinutes}")
	private int lockoutMinutes;
	
	@Value("${passwordReset.rpTokenExpiryMinutes}")
	private int rpTokenExpiryMinutes;
	
	@Autowired
	private CredentialMapper credentialMapper;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AmazonSesUtil sesUtil;
	
	@Override
	public ResponseEntity<Object> authenticate(String email, String password, String storeName) {
		log.info("Inside of the CustomerServiceImpl :: authenticate method");
		Customer customer = credentialMapper.findByEmailAndStore(email, storeName);

		if (customer == null) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.BAD_CREDENTIALS);
		
		if (!isPasswordValid(password, customer.getPassword())) {
			processAuthenticationFailure(customer);
			if (isCustomerLocked(customer))
				return ResponseHandler.generateResponse(Constants.ACCOUNT_LOCKED, HttpStatus.OK, "ACCOUNT_LOCKED");	
			else
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.BAD_CREDENTIALS);	
		}

		if (customer.getLockExpires() != null) {
			unlock(customer);	
		}
		
		if(customer.getStatus() == UserStatus.NOT_CONFIRMED)
			return ResponseHandler.generateResponse(Constants.ACCOUNT_NOT_CONFIRMED, HttpStatus.OK, "ACCOUNT_NOT_CONFIRMED");
		
		if (customer.getStatus() == UserStatus.INACTIVE)
			return ResponseHandler.generateResponse(Constants.ACCOUNT_INACTIVE, HttpStatus.OK, "ACCOUNT_INACTIVE");		
		
		return ResponseHandler.generateResponse("Success", HttpStatus.OK, modelMapper.map(customer, LoginResponse.class));
	}
	
	private void processAuthenticationFailure(Customer customer) {
		Timestamp now = new Timestamp(new Date().getTime());
		Timestamp lockExpiry = customer.getLockExpires();
		
		int failureNum = customer.getFailuresNum() + 1;
		boolean isLockExpired = lockExpiry != null && now.getTime() > lockExpiry.getTime();
		
		// set first failure date when this is the first failure or the lock is expired
		if(failureNum == 1 || customer.getFirstFailure() == null || isLockExpired) {
			failureNum = 1;
			customer.setFirstFailure(now);
			customer.setLockExpires(null);
			// otherwise lock customer
		} else if (failureNum >= maxFailedAttampt){
			customer.setAccountLockYn("Y");
			customer.setLockExpires(
					Timestamp.from(new Timestamp(now.getTime()).toInstant().plusSeconds(lockoutMinutes * 60)));
		}
		
		customer.setFailuresNum(failureNum);
		credentialMapper.updateAccountLock(customer);
	}
	
	private void unlock(Customer customer) {
		if(customer != null) {
			customer.setFailuresNum(0);
			customer.setFirstFailure(null);
			customer.setLockExpires(null);
			customer.setAccountLockYn("N");
	
			credentialMapper.updateAccountLock(customer);
		}
	}
	
	private boolean isCustomerLocked(Customer customer) {
		if (customer.getLockExpires() != null) {
			Timestamp now = new Timestamp(new Date().getTime());
			return customer.getLockExpires().compareTo(now) > 0;
		}
			
		return false;
	}

	private boolean isPasswordValid(String password, String hash) {
		String[] hashFields = hash.split(":");
		String newHash = PasswordEncryptionArgon2.generateArgon2idToHex(password, hashFields[1]);
		return newHash.equals(hashFields[0]);
    }

	@Override
	public ResponseEntity<Object> resetPasswordInit(String email, String storeName) {
		log.info("Password reset request for email :{} and store :{}", email, storeName);
		
		Customer customer = credentialMapper.findByEmailAndStore(email, storeName);
		if (customer == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.EMAIL_NOT_REGISTERED);	

		if(customer.getStatus() == UserStatus.NOT_CONFIRMED)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ACCOUNT_NOT_CONFIRMED);
		
		if (customer.getStatus() == UserStatus.INACTIVE)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ACCOUNT_INACTIVE);		

		String rpToken = RandomUtil.randomAlphanumeric(32);

		credentialMapper.savePasswordResetToken(email, storeName, rpToken);
		log.info("Password reset request token :{} generated for email :{} and store :{}", rpToken, email, storeName);
		
		Map<String, Object> properties = new HashMap<>();

		String link = String.format("http://localhost:8080/reset-password?email=%s&token=%s", email, rpToken);
		properties.put("link", link);
		
		log.info("Sending reset password link to email :{} and store:{}", email, storeName);
		sesUtil.sendEmail(new MailBody(email, "Reset Password Request", "reset-password", properties));
		log.info("Reset password link sent to email :{} and store:{}", email, storeName);
		
		return ResponseHandler.generateResponse(Constants.RESET_PASSWORD_LINK_SENT, HttpStatus.OK, "Success");
	}

	@Override
	public ResponseEntity<Object> resetPasswordFinish(String email, String rpToken, String password, String storeName) {
		Customer customer = credentialMapper.findByEmailAndStore(email, storeName);
		if (customer == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.EMAIL_NOT_REGISTERED);	
		
		if (!rpToken.equals(customer.getRpToken()))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.RP_TOKEN_NOTFOUND);		
		
		Timestamp currentTimestamp = new Timestamp(new Date().getTime());
		long minuteDifference = ((currentTimestamp.getTime() - customer.getRpTokenCreatedAt().getTime()) / 1000) / 60;
		
		if (minuteDifference  >= rpTokenExpiryMinutes)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.RP_TOKEN_EXPIRED);
		
		String newPassword = PasswordEncryptionArgon2.generateArgon2idToHex(password);
		credentialMapper.resetPassword(email, storeName, newPassword);

		return ResponseHandler.generateResponse(Constants.RESET_PASSWORD_SUCCESS, HttpStatus.OK, "Success");
	}
}
