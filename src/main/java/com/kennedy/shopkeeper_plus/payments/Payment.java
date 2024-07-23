package com.kennedy.shopkeeper_plus.payments;

import com.kennedy.shopkeeper_plus.Utils.EntityStatus;
import com.kennedy.shopkeeper_plus.creditDebt.CreditDebt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "payment_id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "credit_debt_id")
	private CreditDebt creditDebt;

	@Column(name = "payment_amount")
	private BigDecimal amount;

	@Column(name = "payment_date")
	private LocalDateTime date;

	@Enumerated(EnumType.STRING)
	private EntityStatus status = EntityStatus.ACTIVE;
}
