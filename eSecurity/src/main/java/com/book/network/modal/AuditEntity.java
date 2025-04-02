package com.book.network.modal;

import java.util.Date;

import org.slf4j.MDC;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author aravinth
 * @since 2024
 *
 *        A sample source file for the code formatter preview
 */
@Getter
@Setter
@MappedSuperclass
public class AuditEntity {

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	@Column(updatable = false)
	private Long createdBy;

	private Long updatedBy;

	private Integer deleteFlag;

	@PrePersist
	protected void onCreate() {
		this.createdOn = new Date();
		this.deleteFlag = 0;
		this.createdBy = Long.valueOf(MDC.get("user"));
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedOn = new Date();
		this.updatedBy = Long.valueOf(MDC.get("user"));
	}

}