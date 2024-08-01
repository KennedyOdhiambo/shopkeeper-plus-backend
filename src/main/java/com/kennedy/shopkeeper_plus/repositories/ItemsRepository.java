package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ItemsRepository extends JpaRepository<Item, UUID> {

	Optional<Item> findByIdAndStatus(UUID id, EntityStatus status);
}
