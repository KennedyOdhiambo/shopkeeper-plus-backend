package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
	Optional<Category> findByIdAndStatus(UUID id, EntityStatus status);

	Optional<Category> findByNameAndStatus(String name, EntityStatus status);

	List<Category> findByStatus(EntityStatus status);

	@Query("SELECT c FROM Category c WHERE c.user.id = :userId AND c.status = 'ACTIVE'")
	List<Category> findByUserId(@Param("userId") UUID userId);
}
