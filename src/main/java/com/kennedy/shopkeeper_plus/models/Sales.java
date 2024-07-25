package com.kennedy.shopkeeper_plus.models;

import com.kennedy.shopkeeper_plus.enums.PaymentOptions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sales")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Sales extends BaseEntity {


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

	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<SalesItem> salesItems;

}
