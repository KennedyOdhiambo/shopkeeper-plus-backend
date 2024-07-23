package com.kennedy.shopkeeper_plus.creditDebt;

import com.kennedy.shopkeeper_plus.Utils.EntityStatus;
import com.kennedy.shopkeeper_plus.customers.Customer;
import com.kennedy.shopkeeper_plus.sales.Sales;
import com.kennedy.shopkeeper_plus.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "credit_debt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDebt {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "credit_debt_id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne
	@JoinColumn(name = "sales_id")
	private Sales sales;

	@Column(name = "transaction_date")
	private LocalDateTime date;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Column(name = "transaction_amount")
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@Column(name = "transaction_type", nullable = false)
	private TransactionType transactionType;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EntityStatus status = EntityStatus.ACTIVE;
}
