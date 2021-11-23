package com.demo.request;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter(AccessLevel.PUBLIC)
public class CustomerRequest {

	@ApiModelProperty
	private String email;
	
	@ApiModelProperty(position = 1)
	private String password;
    
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
    
}