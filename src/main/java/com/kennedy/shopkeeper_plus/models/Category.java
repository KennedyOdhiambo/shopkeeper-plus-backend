package com.kennedy.shopkeeper_plus.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor

public class Category extends BaseEntity {


	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "category_name")
	private String name;

	@Column(name = "description")
	private String description;

	@JsonBackReference
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<Item> items;

}
