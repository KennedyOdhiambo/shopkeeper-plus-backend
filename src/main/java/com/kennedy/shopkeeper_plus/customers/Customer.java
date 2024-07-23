package com.kennedy.shopkeeper_plus.customers;

import com.kennedy.shopkeeper_plus.Utils.EntityStatus;
import com.kennedy.shopkeeper_plus.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "customer_id")
	private UUID customerId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "customer_name", nullable = false, length = 256)
	private String name;

	@Column(name = "customer_contact", nullable = false, length = 256)
	private String contact;

	@Column(name = "kra_pin", length = 256)
	private String kraPin;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EntityStatus status = EntityStatus.ACTIVE;

}
