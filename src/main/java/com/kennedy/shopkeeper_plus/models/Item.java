package com.kennedy.shopkeeper_plus.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "items")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor

public class Item extends BaseEntity {


	@Column(name = "item_name", nullable = false)
	private String name;

	@Column(name = "unit_of_measure", nullable = false)
	private String unitOfMeasure;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "reorder_level")
	private Integer reorderLevel;

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Inventory> inventory;
}