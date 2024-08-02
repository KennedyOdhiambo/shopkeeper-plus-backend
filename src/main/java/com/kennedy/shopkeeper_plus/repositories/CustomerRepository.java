package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
	Optional<Customer> findByIdAndStatus(UUID id, EntityStatus status);

	@Query("SELECT c " +
			       "FROM Customer c " +
			       "WHERE c.status = 'ACTIVE' " +
			       "AND c.user.id = :userId")
	Page<Customer> findActiveCustomersByUserId(@Param("userId") UUID userId, Pageable pageable);
}
