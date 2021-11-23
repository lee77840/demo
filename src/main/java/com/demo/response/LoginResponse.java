package com.demo.response;

import com.demo.constant.UserStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

	@ApiModelProperty
	private String email;
    
	@ApiModelProperty(position = 2)
    private String customerId;
    
	@ApiModelProperty(position = 3)
    private String storeName;
    
	@ApiModelProperty(position = 4)
    private String firstname;
    
	@ApiModelProperty(position = 5)
    private String lastname;
    
	@ApiModelProperty(position = 6)
    private String orgName;
	
	@ApiModelProperty(position = 7)
    private String newsSubscribe;
	
	@ApiModelProperty(position = 8)
	private String activeYn;
	
	@ApiModelProperty(position = 9)
	private String accountLockYn;
	
	@ApiModelProperty(position = 10)
	private UserStatus status;
}
