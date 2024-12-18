package com.example.ecommerce.controller;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public Cart getCart(@PathVariable Long id) {
        return cartService.getCart(id);
    }

    @PutMapping("/{cartId}/add-product/{productId}")
    public Cart addProductToCart(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity) {
        return cartService.updateCart(cartId, productId, quantity);
    }

    @PutMapping("/{cartId}/update-product/{productId}")
    public Cart updateCart(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity) {
        return cartService.updateCart(cartId, productId, quantity);
    }

    @PutMapping("/{cartId}/empty")
    public void emptyCart(@PathVariable Long cartId) {
        cartService.emptyCart(cartId);
    }
}
