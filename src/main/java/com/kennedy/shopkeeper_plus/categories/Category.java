package com.kennedy.shopkeeper_plus.categories;

import com.kennedy.shopkeeper_plus.Utils.EntityStatus;
import com.kennedy.shopkeeper_plus.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "category_id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "category_name")
	private String name;

	@Column(name = "description")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EntityStatus status = EntityStatus.ACTIVE;
}
