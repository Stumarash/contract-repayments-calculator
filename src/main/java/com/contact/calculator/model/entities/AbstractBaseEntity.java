package com.contact.calculator.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractBaseEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "lock_version", columnDefinition = "BIGINT NOT NULL DEFAULT 0")
	@Version
	private Long lockVersion;

	@Column(name = "created", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
	@CreationTimestamp
	private LocalDateTime created;

	@Column(name = "updated", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
	@UpdateTimestamp
	private LocalDateTime updated;

	protected AbstractBaseEntity(AbstractBaseEntity abstractBaseEntity) {
		this.id = abstractBaseEntity.getId();
		this.lockVersion = abstractBaseEntity.getLockVersion();
		this.created = abstractBaseEntity.getCreated();
		this.updated = abstractBaseEntity.updated;
	}

}
