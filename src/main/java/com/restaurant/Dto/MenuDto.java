package com.restaurant.Dto;

import lombok.Data;

@Data
public class MenuDto {
    private String name;
    private Double price;
    private Integer stock;
    private Long restaurantId;
}