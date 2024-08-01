package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
	Optional<Category> findByIdAndStatus(UUID id, EntityStatus status);

	Optional<Category> findByNameAndStatus(String name, EntityStatus status);
}
