package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemsRepository extends JpaRepository<Item, UUID> {

	Optional<Item> findByIdAndStatus(UUID id, EntityStatus status);

	@Query("SELECT i FROM Item i WHERE i.category.id = :categoryId AND i.status = 'ACTIVE'")
	List<Item> findActiveByCategoryId(@Param("categoryId") UUID categoryId);

}
