package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
	@Query("SELECT i FROM Inventory i " +
			"WHERE i.item.id = :itemId " +
			"AND i.status = 'ACTIVE' " +
			"AND i.quantityInStock > 0 " +
			"ORDER BY i.lastUpdated ASC")
	List<Inventory> findAvailableInventoryByItemId(@Param("itemId") UUID itemId);
}
