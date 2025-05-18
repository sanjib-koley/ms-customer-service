package com.sanjib.edureka.ms_customer_service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name="item")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id", nullable=false)
	private Integer itemId;
	
	
	@Column(name = "product_id", nullable=false)
	private Integer productId;
	

	@Column(name = "product_name", nullable=false,length=100)
	private String productName;
	
	@NotNull
	@Min(value = 0)
	@Column(name = "quantity", nullable = false)
	private Integer quantity=0;
	
	
	@DecimalMin(value = "0.00")
	@Column(name = "price", nullable=false)
	private Double price;
	
	
	@ManyToOne
	@JoinColumn(name = "cart_id")
	@JsonIgnore
	private Cart cart;
	
}
