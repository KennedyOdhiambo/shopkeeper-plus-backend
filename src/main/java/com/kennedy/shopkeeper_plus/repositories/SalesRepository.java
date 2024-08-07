package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.enums.PaymentOptions;
import com.kennedy.shopkeeper_plus.models.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.UUID;


public interface SalesRepository extends JpaRepository<Sales, UUID> {
	@Query("SELECT s FROM Sales s WHERE " +
			       "(:startDate IS NULL OR s.salesDate >= CAST(:startDate AS date)) AND " +
			       "(:endDate IS NULL OR s.salesDate <= CAST(:endDate AS date)) AND " +
			       "(:paymentOptions IS NULL OR s.paymentOption = :paymentOptions) AND " +
			       "(:customerId IS NULL OR s.customer.id = :customerId) AND " +
			       "s.status = 'ACTIVE'")
	Page<Sales> findByFilters(@Param("startDate") LocalDate startDate,
	                          @Param("endDate") LocalDate endDate,
	                          @Param("paymentOptions") PaymentOptions paymentOptions,
	                          @Param("customerId") UUID customerId,
	                          Pageable pageable);

}
