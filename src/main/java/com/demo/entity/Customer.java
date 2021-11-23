package com.demo.entity;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.demo.constant.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name="customer")
@EqualsAndHashCode(callSuper = true)
public class Customer extends AuditEntity {

	@Id
	@Column(length = 100)
	private String email;
	
	@Column(length = 100)
	private String password;
    
	@Column(length = 50)
    private String customerId;
    
    @Column(length = 50)
    private String storeName;
    
    @Column(length = 100)
    private String firstname;
    
    @Column(length = 100)
    private String lastname;
    
    @Column(length = 50)
    private String orgName;
    
    @Column(length = 20)
    private String groupId;
    
    @Column(length = 1)
    private String extentionAttributes;
    
    @Column(length = 100)
    private String company;
    
    @Column(length = 12)
    private String phoneNumber;
    
    @Column(length = 255)
    private String address1;
    
    @Column(length = 255)
    private String address2;
    
    @Column(length = 30)
    private String city;
    
    @Column(length = 20)
    private String state;
    
    @Column(length = 5)
    private String zipCode;
    
    @Column(length = 50)
    private String country;
    
    @Column(length = 1)
    private String newsSubscribe;
    
    @Column(length = 1)
    private String activeYn;
    
    @Column(length = 1)
    private String accountLockYn;
    
    @Column(length = 2)
    private int failuresNum;
    
    @Column
    private Timestamp firstFailure;
    
    @Column
    private Timestamp lockExpires;
    
    @Column
    private UserStatus status;
 
    // Reset Password Token
    @Column
    private String rpToken;
    
    // Rest Password Token created time
    @Column
    private Timestamp rpTokenCreatedAt;
}