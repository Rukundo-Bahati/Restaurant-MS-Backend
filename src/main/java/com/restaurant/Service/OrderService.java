package com.restaurant.Service;

import com.restaurant.Dto.OrderDto;
import com.restaurant.Entity.Order;

import java.util.List;

public interface OrderService {
    void placeOrder(OrderDto orderDto, String userEmail);

    List<Order> getAllOrders();

    Order getOrderById(Long id);

    Order updateOrder(Long id, OrderDto orderDto);

    void deleteOrder(Long id);
}
