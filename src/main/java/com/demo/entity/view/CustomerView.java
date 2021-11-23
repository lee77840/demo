package com.demo.entity.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter(AccessLevel.PUBLIC)
@Entity(name="customer_view")
public class CustomerView {

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
    
}