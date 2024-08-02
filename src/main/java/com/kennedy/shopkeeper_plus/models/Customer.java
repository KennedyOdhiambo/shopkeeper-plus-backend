package com.kennedy.shopkeeper_plus.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "customers")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {


	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "customer_name", nullable = false, length = 256)
	private String name;

	@Column(name = "customer_contact", nullable = false, length = 256)
	private String contact;

	@Column(name = "kra_pin", length = 256)
	private String kraPin;

	@JsonBackReference
	@OneToMany(mappedBy = "customer")
	private List<Sales> sales;


}
