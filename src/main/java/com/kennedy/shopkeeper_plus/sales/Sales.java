package com.kennedy.shopkeeper_plus.sales;

import com.kennedy.shopkeeper_plus.Utils.EntityStatus;
import com.kennedy.shopkeeper_plus.customers.Customer;
import com.kennedy.shopkeeper_plus.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sales {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "sales_id")
	private UUID salesId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "sales_date")
	private LocalDate salesDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_option")
	private PaymentOptions paymentOption = PaymentOptions.CASH;

	@Column(name = "total_cost")
	private BigDecimal totalCost;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EntityStatus status = EntityStatus.ACTIVE;

}
