package com.sanjib.edureka.ms_customer_service;

public interface CartService {

	public Cart addProductToCart(String token, String usertype, Cart cart,Item item);
	
	public Cart findCartByCartId(Integer cartId);
	
	public Cart updateProductToCart(String token, String usertype, Cart cart,Item item);
	
	public Cart updatePaymentAndAddressToCart(Cart cart,String paymentInfo,String address);
}
