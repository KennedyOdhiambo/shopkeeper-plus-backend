package com.kennedy.shopkeeper_plus.models;

import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EntityStatus status = EntityStatus.ACTIVE;
}
