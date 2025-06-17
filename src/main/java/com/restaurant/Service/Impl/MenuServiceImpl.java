package com.restaurant.Service.Impl;

import com.restaurant.Dto.MenuDto;
import com.restaurant.Entity.Menu;
import com.restaurant.Entity.Restaurant;
import com.restaurant.Exception.ResourceNotFoundException;
import com.restaurant.Repository.MenuRepository;
import com.restaurant.Repository.RestaurantRepository;
import com.restaurant.Service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Menu createMenu(MenuDto menuDto) {
        Restaurant restaurant = restaurantRepository.findById(menuDto.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + menuDto.getRestaurantId()));


        Menu menu = Menu.builder()
                .name(menuDto.getName())
                .price(menuDto.getPrice())
                .stock(menuDto.getStock())
                .restaurant(restaurant)
                .build();

        return menuRepository.save(menu);
    }

    @Override
    public Menu updateMenu(Long id, MenuDto menuDto) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        menu.setName(menuDto.getName());
        menu.setPrice(menuDto.getPrice());
        menu.setStock(menuDto.getStock());

        return menuRepository.save(menu);
    }

    @Override
    public void deleteMenu(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new RuntimeException("Menu not found");
        }
        menuRepository.deleteById(id);
    }

    @Override
    public List<Menu> getMenusByRestaurant(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found");
        }
        return menuRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }


}
