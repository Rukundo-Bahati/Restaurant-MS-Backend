package com.restaurant.Dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long menuId;
    private Integer quantity;
}