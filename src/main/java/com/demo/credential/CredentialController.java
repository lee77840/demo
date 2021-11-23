package com.demo.credential;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.demo.constant.Constants;
import com.demo.credential.service.CredentialService;
import com.demo.request.LoginRequest;
import com.demo.request.ResetPasswordRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "Credential APIs")
@RequestMapping("/accountserviceapi/v1/credential")
public class CredentialController {

	@Autowired
	private CredentialService credentialService;

	@PostMapping("/checkAuthenticate")
	@ApiOperation(value = "Customer Authentication API")
	@ApiResponses(value = { 
			@ApiResponse(code = 400, message = Constants.BAD_REQUEST),
			@ApiResponse(code = 403, message = Constants.ACCESS_DENIED),
			@ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR) })
	public @ResponseBody ResponseEntity<Object> checkAuthenticate(@RequestHeader(value = "store") String storeName,
																  @ApiParam("Login Request Body") @RequestBody @Valid LoginRequest loginRequest) {
		log.debug("Start of CredentialController::checkAuthenticate method");
		return credentialService.authenticate(loginRequest.getEmail(), loginRequest.getPassword(), storeName);
	}
	
	@PostMapping("/resetPassword/init")
	@ApiOperation(value = "Customer Reset Password Init API")
	@ApiResponses(value = { 
			@ApiResponse(code = 400, message = Constants.BAD_REQUEST),
			@ApiResponse(code = 403, message = Constants.ACCESS_DENIED),
			@ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR) })
	public @ResponseBody ResponseEntity<Object> initResetPassword(@RequestHeader(value = "store") String storeName,
			@ApiParam("Customer Email") @RequestParam @NotBlank String email) {
		log.debug("Start of CredentialController::initResetPassword method");
		return credentialService.resetPasswordInit(email, storeName);
	}
	
	@PostMapping("/resetPassword/finish")
	@ApiOperation(value = "Customer Reset Password Finish API")
	@ApiResponses(value = { 
			@ApiResponse(code = 400, message = Constants.BAD_REQUEST),
			@ApiResponse(code = 403, message = Constants.ACCESS_DENIED),
			@ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR) })
	public @ResponseBody ResponseEntity<Object> finishResetPassword(@RequestHeader(value = "store") String storeName,
			@ApiParam("Password Rest Request Body") @RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
		log.debug("Start of CredentialController::finishResetPassword method");
		return credentialService.resetPasswordFinish(resetPasswordRequest.getEmail(), resetPasswordRequest.getRpToken(),
				resetPasswordRequest.getPassword(), storeName);
	}
}
