package com.kennedy.shopkeeper_plus.BusinessTypes;

import com.kennedy.shopkeeper_plus.Utils.EntityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "business_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessType {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "business_type_id")
	private UUID businessTypeId;

	@Column(name = "business_type", nullable = false, length = 256)
	private String businessTypeName;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EntityStatus status = EntityStatus.ACTIVE;
}
