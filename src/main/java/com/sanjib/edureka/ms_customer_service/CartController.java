package com.sanjib.edureka.ms_customer_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
    TokenService tokenService;

	@PostMapping("/cart/add/{productId}/{quantity}")
	public ResponseEntity<?> addProductToCart(@PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity,
			@RequestHeader("Authorization") String token, @RequestHeader("Usertype") String usertype,
			HttpServletResponse httpServletResponse,
            HttpServletRequest httpServletRequest) {

		if (tokenService.validateToken(token) && "customer".equalsIgnoreCase(usertype)) {
			
			Cart cart = new Cart();
			Long customerId = tokenService.getUserIdFromToken(token);
			cart.setCustomerId(customerId);
			
			cart.setProductId(productId);
			cart.setQuantity(quantity);
			Cart cartCreated = cartService.addProductToCart(token,usertype, cart);
			
			String cookieContent = customerId +"@@"+ cartCreated.getCartId();
			
			Cookie cookie = new Cookie("customer-cart",cookieContent);
            cookie.setMaxAge(3600);
            httpServletResponse.addCookie(cookie);

			return new ResponseEntity<Cart>(cartCreated, HttpStatus.CREATED);
		} else {
			return ResponseEntity.status(401).body("Invalid Details");
		}

	}
}
