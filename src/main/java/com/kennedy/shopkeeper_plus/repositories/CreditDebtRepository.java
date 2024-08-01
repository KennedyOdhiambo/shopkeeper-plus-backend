package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.models.CreditDebt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditDebtRepository extends JpaRepository<CreditDebt, UUID> {
}
