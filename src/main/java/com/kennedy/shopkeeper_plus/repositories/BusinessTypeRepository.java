package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.models.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BusinessTypeRepository extends JpaRepository<BusinessType, UUID> {
	List<BusinessType> findByStatus(EntityStatus status);

	Optional<BusinessType> findByName(String name);

	Optional<BusinessType> findByIdAndStatus(UUID id, EntityStatus status);
}
