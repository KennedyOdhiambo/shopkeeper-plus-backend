package com.kennedy.shopkeeper_plus.users;

import com.kennedy.shopkeeper_plus.Utils.EntityStatus;
import com.kennedy.shopkeeper_plus.businessTypes.BusinessType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "full_name", nullable = false, length = 256)
	private String fullName;

	@Column(name = "phone_number", nullable = false, length = 256)
	private String phoneNumber;

	@Column(nullable = false, length = 256)
	private String password;

	@Column(name = "business_name", nullable = false, length = 256)
	private String businessName;

	@ManyToOne
	@JoinColumn(name = "business_type_Id")
	private BusinessType businessType;

	@Column(name = "business_location", length = 256)
	private String businessLocation;

	@Column(name = "date_joined")
	private LocalDateTime dateJoined;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EntityStatus status = EntityStatus.ACTIVE;

}
