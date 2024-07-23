package com.kennedy.shopkeeper_plus.sales.SalesItems;

import com.kennedy.shopkeeper_plus.Utils.EntityStatus;
import com.kennedy.shopkeeper_plus.inventories.Inventory;
import com.kennedy.shopkeeper_plus.items.Item;
import com.kennedy.shopkeeper_plus.sales.Sales;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "sales_items")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SalesItem {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "sales_item_id")
	private UUID salesItemId;

	@ManyToOne
	@JoinColumn(name = "sales_id")
	private Sales sales;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EntityStatus status = EntityStatus.ACTIVE;
}
