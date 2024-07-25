package com.kennedy.shopkeeper_plus.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor

public class Payment extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "credit_debt_id")
	private CreditDebt creditDebt;

	@Column(name = "payment_amount")
	private BigDecimal amount;

	@Column(name = "payment_date")
	private LocalDateTime date;

}
