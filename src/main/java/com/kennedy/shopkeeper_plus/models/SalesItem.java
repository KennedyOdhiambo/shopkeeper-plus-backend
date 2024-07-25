package com.kennedy.shopkeeper_plus.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "sales_items")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor

public class SalesItem extends BaseEntity {


	@ManyToOne
	@JoinColumn(name = "sales_id")
	private Sales sale;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne
	@JoinColumn(name = "inventory_id")
	private Inventory inventory;

	@Column(name = "sales_quantity", nullable = false)
	private Integer salesQuantity;

	@Column(name = "unit_price")
	private BigDecimal unitPrice;

	@Column(name = "totalPrice")
	private BigDecimal totalPrice;


}
