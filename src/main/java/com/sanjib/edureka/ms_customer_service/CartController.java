package com.sanjib.edureka.ms_customer_service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import other_service_bean.Order;
import other_service_bean.Payment;

@RestController
@RequestMapping("/api/v1")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
    TokenService tokenService;
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@PostMapping("/cart/add")
	public ResponseEntity<?> addProductToCart(
			@RequestHeader("Authorization") String token, @RequestHeader("Usertype") String usertype,
			@RequestBody Item itemView,
			HttpServletResponse httpServletResponse,
            HttpServletRequest httpServletRequest) {
		
		boolean isCartPresent = false;
		List<Cookie> cookieList = null;

		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies == null) {
			cookieList = new LinkedList<>();
		} else {
			cookieList = List.of(cookies);
		}

		if (cookieList.stream().filter(cookie -> cookie.getName().equalsIgnoreCase("customerId_cartId")).count()>0) {
			isCartPresent = true;
		}
		System.out.println("isCartPresent:::"+isCartPresent);

		if (tokenService.validateToken(token) && "customer".equalsIgnoreCase(usertype)) {
			
			if(!isCartPresent) {
				
				Item item = new Item();
				item.setProductId(itemView.getProductId());
				item.setQuantity(itemView.getQuantity());
				
				Cart cart = new Cart();
				item.setCart(cart);
				Long customerId = tokenService.getUserIdFromToken(token);
				cart.setCustomerId(customerId);
				
				Cart cartCreated = cartService.addProductToCart(token,usertype, cart,item);
				
				String cookieContent = customerId +"_"+ cartCreated.getCartId();
				Cookie cookie = new Cookie("customerId_cartId",cookieContent);
	            cookie.setMaxAge(3600);
	            
	            httpServletResponse.addCookie(cookie);
	            return new ResponseEntity<Cart>(cartCreated, HttpStatus.CREATED);
			}else {
				
				Cookie cookieCart = cookieList.stream().filter(cookie -> cookie.getName().equalsIgnoreCase("customerId_cartId")).findFirst().get();
				//Integer customerId = Integer.valueOf(cookieCart.getValue().split("_")[0]);
				Integer cartId = Integer.valueOf(cookieCart.getValue().split("_")[1]);
				
				Item item = new Item();
				item.setProductId(itemView.getProductId());
				item.setQuantity(itemView.getQuantity());
				
				Cart cartUpdated = cartService.findCartByCartId(cartId);
				item.setCart(cartUpdated);
				cartService.updateProductToCart(token,usertype, cartUpdated,item);
				
				return new ResponseEntity<Cart>(cartUpdated, HttpStatus.CREATED);
			}
			
		} else {
			return ResponseEntity.status(401).body("Invalid Details");
		}
	

	}
	
	@PutMapping("/cart/add/addressandpayment")
	public ResponseEntity<?> addPaymentToCart(@RequestHeader("Authorization") String token,
			@RequestHeader("Usertype") String usertype, @RequestBody Cart cartView,
			HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {

		boolean isCartPresent = false;
		List<Cookie> cookieList = null;

		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies == null) {
			cookieList = new LinkedList<>();
		} else {
			cookieList = List.of(cookies);
		}

		if (cookieList.stream().filter(cookie -> cookie.getName().equalsIgnoreCase("customerId_cartId")).count() > 0) {
			isCartPresent = true;
		}
		if (tokenService.validateToken(token) && "customer".equalsIgnoreCase(usertype) && isCartPresent) {

			Cookie cookieCart = cookieList.stream()
					.filter(cookie -> cookie.getName().equalsIgnoreCase("customerId_cartId")).findFirst().get();
			Integer cartId = Integer.valueOf(cookieCart.getValue().split("_")[1]);
			Cart cartUpdated = cartService.findCartByCartId(cartId);
			cartService.updatePaymentAndAddressToCart(cartUpdated, cartView.getPaymentInfo(), cartView.getAddress());
			return new ResponseEntity<Cart>(cartUpdated, HttpStatus.OK);

		} else {
			return ResponseEntity.status(401).body("Invalid Details");
		}

	}
	
	@PostMapping("/cart/checkout")
	public ResponseEntity<?> checkoutCart(@RequestHeader("Authorization") String token,
			@RequestHeader("Usertype") String usertype, HttpServletResponse httpServletResponse,
			HttpServletRequest httpServletRequest) {

		boolean isCartPresent = false;
		List<Cookie> cookieList = null;

		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies == null) {
			cookieList = new LinkedList<>();
		} else {
			cookieList = List.of(cookies);
		}

		if (cookieList.stream().filter(cookie -> cookie.getName().equalsIgnoreCase("customerId_cartId")).count() > 0) {
			isCartPresent = true;
		}
		if (tokenService.validateToken(token) && "customer".equalsIgnoreCase(usertype) && isCartPresent) {
			
			Cookie cookieCart = cookieList.stream()
					.filter(cookie -> cookie.getName().equalsIgnoreCase("customerId_cartId")).findFirst().get();
			Integer cartId = Integer.valueOf(cookieCart.getValue().split("_")[1]);
			Long customerId = Long.valueOf(cookieCart.getValue().split("_")[0]);
			Cart cartRetrieved = cartService.findCartByCartId(cartId);
			
			Order orderCreated = tokenService.createOrder(token, usertype, customerId, cartId, cartRetrieved);
			kafkaTemplate.send("order_complete", orderCreated.getId());
			
			Payment paymentView = new Payment();
			paymentView.setCustomerId(customerId);
			paymentView.setBalance(orderCreated.getOrderValue());
			tokenService.debitPayment(token, usertype, orderCreated.getId(), paymentView);
			
			orderCreated.getItems().stream().forEach(
					item -> tokenService.debitInventory(token, usertype, item.getProductId(), item.getQuantity()));
			
			
			
			return ResponseEntity.status(200).body("Checkout initiated:::"+orderCreated.getId());

		} else {
			return ResponseEntity.status(401).body("Invalid Details");
		}

	}
}
