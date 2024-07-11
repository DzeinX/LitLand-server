package org.example.litland.service;

import org.example.litland.model.Cart;
import org.example.litland.model.Order;
import org.example.litland.model.User;
import org.example.litland.repository.OrderRepository;
import org.example.litland.repository.UserRepository;
import org.example.litland.response.OrderResponse;
import org.example.litland.shell.CartShell;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }
    
    public List<OrderResponse> findAllByUser(String hash) {
        Optional<User> user = userRepository.findById(Long.parseLong(hash));
        if (user.isEmpty()) {
            return new ArrayList<>();
        }

        List<Order> orders = orderRepository.findAllByUserId(user.get().getId());
        
        List<OrderResponse> orderResponses = new ArrayList<>();
        
        for (Order order : orders) {
            List<CartShell> cartShells = this.getCartShells(order);
            orderResponses.add(new OrderResponse(null, cartShells, "", ""));
        }

        return orderResponses.reversed();
    }

    private List<CartShell> getCartShells(Order order) {
        List<CartShell> cartShells = new ArrayList<>();
        for (Cart cart : order.getCarts()) {
            CartShell cartShell = new CartShell(
                    cart.getBook().getId().toString(),
                    cart.getBook().getName(),
                    cart.getBook().getAuthors(),
                    cart.getAmount(),
                    cart.getBook().getPrice(),
                    cart.getBook().getCoverName(),
                    cart.getStatus()
            );
            cartShells.add(cartShell);
        }
        return cartShells;
    }
}
