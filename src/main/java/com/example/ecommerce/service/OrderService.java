package com.example.ecommerce.service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;  // CartService'i inject edin

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;  // CartService'i inject edin
    }

    public Order placeOrder(Long cartId) {
        Cart cart = cartService.getCart(cartId);  // CartService'i çağırın

        Order order = new Order();
        order.setCustomer(cart.getCustomer());

        Set<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getPriceAtPurchase());
            return orderItem;
        }).collect(Collectors.toSet());

        order.setItems(orderItems);

        // Siparişin toplam fiyatını güncelle
        double totalPrice = orderItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPriceAtPurchase())
                .sum();
        order.setTotalPrice(totalPrice);

        cartService.emptyCart(cartId);  // Sepeti boşaltın

        return orderRepository.save(order);
    }

    public Order getOrderForCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode).orElseThrow();  // findByOrderCode metodu ile sorgulama yapın
    }
}
