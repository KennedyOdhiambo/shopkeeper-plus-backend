package com.kennedy.shopkeeper_plus.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {


	@Column(name = "full_name", nullable = false, length = 256)
	private String fullName;

	@Column(name = "phone_number", nullable = false, length = 256, unique = true)
	private String phoneNumber;

	@Column(nullable = false, length = 256)
	private String password;

	@Column(name = "business_name", nullable = false, length = 256)
	private String businessName;

	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name = "business_type_Id")
	private BusinessType businessType;

	@Column(name = "business_location", length = 256)
	private String businessLocation;

	@CreatedDate
	@Column(name = "date_joined", nullable = false)
	private LocalDateTime dateJoined;

	@JsonBackReference
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Sales> sales;

	@JsonBackReference
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Customer> customers;

	@JsonBackReference
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Category> categories;


}
