package com.restaurant.Controller;

import com.restaurant.Dto.OrderDto;
import com.restaurant.Entity.Order;
import com.restaurant.Security.JwtAuthenticationProvider;
import com.restaurant.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @PostMapping("/add")
    public ResponseEntity<String> placeOrder(@RequestBody OrderDto orderDto, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer "
        String userEmail = jwtAuthenticationProvider.getUsername(token); // Extract email from JWT

        orderService.placeOrder(orderDto, userEmail);
        return ResponseEntity.ok("Order placed successfully.");
    }



    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully.");
    }
}
