package com.kennedy.shopkeeper_plus.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor

public class Inventory extends BaseEntity {


	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@Column(name = "quantity_added")
	private int quantityAdded;

	@Column(name = "quantity_in_stock")
	private int quantityInStock = 0;

	@Column(name = "buying_price")
	private BigDecimal buyingPrice;

	@Column(name = "selling_price")
	private BigDecimal sellingPrice;

	@Column(name = "last_updated")
	private LocalDateTime lastUpdated;


}
