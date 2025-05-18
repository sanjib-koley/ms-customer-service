package com.sanjib.edureka.ms_customer_service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="cart")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id", nullable=false)
	private Integer cartId;
	
	@Column(name = "customer_id", nullable=false)
	private Long customerId;
	
	
	@Column(name = "product_id", nullable=false)
	private Integer productId;
	

	@Column(name = "product_name", nullable=false,length=100)
	private String productName;
	
	@NotNull
	@Min(value = 0)
	@Column(name = "quantity", nullable = false)
	private Integer quantity=0;
	
	@NotNull
	@DecimalMin(value = "0.00")
	@Column(name = "cart_value", nullable=false)
	private Double cartValue=0.0;
	

	@Size(max = 500, message = "Address size should be between 3-500")
	@Column(name = "address",length = 500)
	private String address;
	
	@Size(max = 500, message = "Address size should be between 3-500")
	@Column(name = "payment_info",length = 500)
	private String paymentInfo;

}
