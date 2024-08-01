package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.models.SalesItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalesItemsRepository extends JpaRepository<SalesItem, UUID> {
}
