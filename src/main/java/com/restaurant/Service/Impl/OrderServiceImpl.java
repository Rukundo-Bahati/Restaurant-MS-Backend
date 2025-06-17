package com.restaurant.Service.Impl;

import com.restaurant.Dto.OrderDto;
import com.restaurant.Entity.Menu;
import com.restaurant.Entity.Order;
import com.restaurant.Exception.ResourceNotFoundException;
import com.restaurant.Repository.MenuRepository;
import com.restaurant.Repository.OrderRepository;
import com.restaurant.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    // CREATE
    @Override
    public void placeOrder(OrderDto orderDto, String userEmail) {
        Menu menu = menuRepository.findById(orderDto.getMenuId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + orderDto.getMenuId()));

        Order order = Order.builder()
                .menu(menu)
                .quantity(orderDto.getQuantity())
                .userEmail(userEmail)
                .build();

        orderRepository.save(order);
    }

    // READ (Get all orders)
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // READ (Get order by ID)
    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    // UPDATE (Change quantity or other details)
    @Override
    public Order updateOrder(Long id, OrderDto orderDto) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        Menu menu = menuRepository.findById(orderDto.getMenuId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + orderDto.getMenuId()));

        existingOrder.setMenu(menu);
        existingOrder.setQuantity(orderDto.getQuantity());

        return orderRepository.save(existingOrder);
    }

    // DELETE
    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }
}
