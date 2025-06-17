package com.restaurant.Controller;

import com.restaurant.Dto.MenuDto;
import com.restaurant.Entity.Menu;
import com.restaurant.Service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/add")
    public ResponseEntity<Menu> create(@RequestBody MenuDto menuDto) {
        return ResponseEntity.ok(menuService.createMenu(menuDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Menu> update(@PathVariable Long id, @RequestBody MenuDto menuDto) {
        return ResponseEntity.ok(menuService.updateMenu(id, menuDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Menu>> getMenusByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(menuService.getMenusByRestaurant(restaurantId));
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getMenus() {
        return ResponseEntity.ok(menuService.getAllMenus());
    }
}
