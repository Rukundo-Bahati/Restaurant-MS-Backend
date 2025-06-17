package com.restaurant.Service;

import com.restaurant.Dto.UserDto;

public interface AuthService {
    void register(UserDto userDto);

    void verifyToken(String token);

    String login(String email, String password);
}