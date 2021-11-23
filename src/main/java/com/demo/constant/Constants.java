package com.demo.constant;

import org.springframework.beans.factory.annotation.Value;

public final class Constants {

    private Constants() {
    }

    // httpclient
    public static final int httpclientConTotal = 50;
    public static final int httpclientConPerRoute = 2000;
    public static final int httpclientConTimeout = 2000;
    public static final int httpclientReadTimeout = 5000;

    // Mail    
    public static final String SENDER = "donotreply@lge.com";
    
    public static final String ACCESS_DENIED = "Access denied";
    public static final String BAD_REQUEST = "Bad Request";
    public static final String NOT_FOUND = "Not Found";
    public static final String FAILED_TO_BUILD_S3_URL = "Failed to build S3 Url";
    public static final String FILE_NOT_FOUND = "File not found";
    public static final String FILE_NOT_EXISTS_ON_S3 = "File not found on S3 bucket";
    public static final String FILE_UPLOAD_ERROR = "File upload failed";
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String FRAME_SLOT_TIME_INVALID = "Frame slot time is invalid";
    public static final String JSON_INVALID = "Malformed JSON request";
    public static final String CREATED = "Created";
    public static final String CONFLICT = "Conflict";
    public static final String REQUEST_HEADER_USER_AGENT = "Crowdflik-User-Agent-Type";
    public static final String EMAIL_REGEX_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String PHONE_REGEX_PATTERN = "^[[+]{0,1}[(]{0,1}[0-9]{1,3}[)]{0,1}[-\\s\\.\\0-9]{9,13}$";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

    // Customer Message
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String CUSTOMER_INPUT_INVALID = "Invalid username/password supplied";
    public static final String CUSTOMER_ALREADY_IN_USE = "Email Address is already in use";
    public static final String CUSTOMER_CREATED = "Customer created successfully";
    public static final String CUSTOMER_UPDATED = "Customer updated successfully";

    // JWT Keys
    public static final String JWT_ACCESS_TOKEN = "access_token";
    public static final String JWT_REFRESH_TOKEN = "refresh_token";
    public static final String JWT_EMAIL = "email";
    public static final String JWT_FIRSTNAME = "firstname";
    public static final String JWT_LASTNAME = "lastname";
    public static final String JWT_PURCHASE_LIMIT = "purchase_limit";
    public static final String JWT_IAT_TIME = "iat";
    public static final String JWT_EXP_TIME = "exp";
    public static final String JWT_ORG_LOG = "org_logo";
    public static final String JWT_STORE_NAME = "store_name";
    public static final String JWT_STORE_DISP_NAME = "storeDisplayName";
    public static final String JWT_ORG_NAME = "org_name";
    public static final String JWT_INVALID_EXPIRED = "Expired or invalid JWT token";
    
	// Mail Message
	public static final String MAIL_FAILED = "Mail service failed";

	// Authentication Message
	public static final String BAD_CREDENTIALS = "The email or password you entered is incorrect";
    public static final String ACCOUNT_NOT_CONFIRMED = "This account isn’t confirmed. Verify and try again";
    public static final String ACCOUNT_INACTIVE = "You account is no longer active";
    public static final String ACCOUNT_LOCKED = "This account has been locked";	

    // Reset Password
    public static final String EMAIL_NOT_REGISTERED = "This email is not registered with us";
    public static final String RESET_PASSWORD_LINK_SENT = "Password reset link has been sent to your email";
    public static final String RP_TOKEN_NOTFOUND = "Your password reset token not found";
    public static final String RP_TOKEN_EXPIRED = "Your password reset token is expired";
    public static final String RESET_PASSWORD_SUCCESS = "Your password has been reset successfully";
}
