package com.sanjib.edureka.ms_customer_service;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {

	private Integer productId;

	private String productName;

	private Double price;

	private String description;
	
	private Integer quantity;

	private CategoryEnum category;

	private ProductStatus status;

	private Long sellerId;

}
