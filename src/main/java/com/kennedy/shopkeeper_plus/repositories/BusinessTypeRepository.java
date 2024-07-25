package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.models.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BusinessTypeRepository extends JpaRepository<BusinessType, UUID> {
	List<BusinessType> findByStatus(String status);
}
