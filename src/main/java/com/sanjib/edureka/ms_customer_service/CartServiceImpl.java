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
	public Cart addProductToCart(String token, String usertype, Cart cart,Item item) {
		
		Product product = tokenService.getProductFromId(token,usertype,item.getProductId());
		if(product.getStatus()==ProductStatus.AVAILABLE) {
			
			item.setProductId(product.getProductId());
			item.setProductName(product.getProductName());
			item.setPrice(product.getPrice());
			
			cart.getItems().add(item);
			cart.setCartValue(cart.getCartValue()+item.getPrice());
			
			cartReository.save(cart);
		}
		return cart;
	}

	@Override
	public Cart findCartByCartId(Integer cartId) {
		return cartReository.findCartByCartId(cartId);
	}
	
	@Override
	public Cart updateProductToCart(String token, String usertype, Cart cart,Item item) {
		
		Product product = tokenService.getProductFromId(token,usertype,item.getProductId());
		if(product.getStatus()==ProductStatus.AVAILABLE) {
			
			item.setProductId(product.getProductId());
			item.setProductName(product.getProductName());
			item.setPrice(product.getPrice());
			
			cart.getItems().add(item);
			cart.setCartValue(cart.getCartValue()+item.getPrice());
			
			cartReository.save(cart);
		}
		return cart;
	}

	@Override
	public Cart updatePaymentAndAddressToCart(Cart cart, String paymentInfo,String address) {
		cart.setPaymentInfo(paymentInfo);
		cart.setAddress(address);
		cartReository.save(cart);
		return cart;
	}
}
