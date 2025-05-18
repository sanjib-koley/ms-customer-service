package com.sanjib.edureka.ms_customer_service ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartReository cartReository;
	
	
	@Autowired
    TokenService tokenService;

	@Override
	public Cart addProductToCart(String token, String usertype, Cart cart) {
		
		Product product = tokenService.getProductFromId(token,usertype,cart.getProductId());
		
		if(product.getStatus()==ProductStatus.AVAILABLE) {
			cart.setProductName(product.getProductName());
			
			cart.setQuantity(cart.getQuantity());
			// to-do
			cart.setCartValue(cart.getCartValue()+product.getPrice());
			
			cartReository.save(cart);
		}
		

		return cart;
	}
}
