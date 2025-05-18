package com.sanjib.edureka.ms_customer_service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CartReository extends JpaRepository<Cart, Integer> {
	
	public Cart findCartByCartId(Integer cartId);
}
