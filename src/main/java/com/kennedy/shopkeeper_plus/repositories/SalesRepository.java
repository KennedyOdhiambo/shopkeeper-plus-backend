package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.models.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface SalesRepository extends JpaRepository<Sales, UUID> {
}
