package com.kennedy.shopkeeper_plus.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kennedy.shopkeeper_plus.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {


	@Column(name = "full_name", nullable = false, length = 256)
	private String fullName;

	@Column(name = "username", nullable = false, length = 256)
	private String username;

	@Column(name = "phone_number", nullable = false, length = 256, unique = true)
	private String phoneNumber;

	@Column(nullable = false, length = 256)
	private String password;

	@Column(name = "business_name", nullable = false, length = 256)
	private String businessName;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;

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


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		return username;
	}

}
