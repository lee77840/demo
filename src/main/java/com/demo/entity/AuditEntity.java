package com.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity {

	@CreatedBy
    @Column(updatable = false)
    protected Long createdBy;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    public Timestamp createdDate;

    @LastModifiedBy
    protected Long lastModifiedBy;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(nullable = false)
    protected Timestamp lastModifiedDate;
	
}
