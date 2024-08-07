package com.kennedy.shopkeeper_plus.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "business_types")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor

public class BusinessType extends BaseEntity {
	@Column(name = "business_type", nullable = false, length = 256, unique = true)
	private String name;

	@JsonBackReference
	@OneToMany(mappedBy = "businessType", fetch = FetchType.EAGER)
	private List<User> users;


}
