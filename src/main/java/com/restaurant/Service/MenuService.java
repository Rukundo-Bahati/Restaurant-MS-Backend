package com.restaurant.Service;

import com.restaurant.Dto.MenuDto;
import com.restaurant.Entity.Menu;
import java.util.List;

public interface MenuService {
    Menu createMenu(MenuDto menuDto);
    Menu updateMenu(Long id, MenuDto menuDto);
    void deleteMenu(Long id);
    List<Menu> getMenusByRestaurant(Long restaurantId);
    List<Menu> getAllMenus();
}
