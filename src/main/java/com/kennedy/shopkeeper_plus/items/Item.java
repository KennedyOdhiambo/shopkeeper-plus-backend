package com.kennedy.shopkeeper_plus.items;

import com.kennedy.shopkeeper_plus.Utils.EntityStatus;
import com.kennedy.shopkeeper_plus.categories.Category;
import com.kennedy.shopkeeper_plus.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "item_id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "item_name", nullable = false)
	private String name;

	@Column(name = "unit_of_measure", nullable = false)
	private String unitOfMeasure;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "reorder_level")
	private Integer reorderLevel;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EntityStatus entityStatus = EntityStatus.ACTIVE;
}
