package com.example.ecommerce.service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart getCart(Long id) {
        return cartRepository.findById(id).orElseThrow();
    }

    public Cart updateCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPriceAtPurchase(product.getPrice());
            cart.getItems().add(cartItem);
        }

        // Fiyatı güncelle
        double totalPrice = cart.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPriceAtPurchase())
                .sum();
        cart.setTotalPrice(totalPrice);

        return cartRepository.save(cart);
    }

    public void emptyCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }
}
