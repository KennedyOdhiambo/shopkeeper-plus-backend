package com.kennedy.shopkeeper_plus.inventories;

import com.kennedy.shopkeeper_plus.Utils.EntityStatus;
import com.kennedy.shopkeeper_plus.items.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "inventory_id")
	private UUID id;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EntityStatus status = EntityStatus.ACTIVE;
}
