package com.restaurant.Service.Impl;

import com.restaurant.Dto.UserDto;
import com.restaurant.Entity.User;
import com.restaurant.Entity.VerificationToken;
import com.restaurant.Enums.Role;
import com.restaurant.Repository.UserRepository;
import com.restaurant.Repository.VerificationTokenRepository;
import com.restaurant.Security.JwtAuthenticationProvider;
import com.restaurant.Service.AuthService;
import com.restaurant.Service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public void register(UserDto userDto) {
        // Save user first
        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.USER)
                .verified(false)
                .build();

        User savedUser = userRepository.save(user);

        // Create and save verification token linked to saved user
        String verificationToken = UUID.randomUUID().toString();
        VerificationToken token = VerificationToken.builder()
                .token(verificationToken)
                .user(savedUser)
                .build();
        tokenRepository.save(token);

        // Send verification email
        emailSenderService.sendVerificationToken(savedUser, verificationToken);
    }

    @Override
    public void verifyToken(String token) {
        System.out.println("Verifying token: " + token);

        Optional<VerificationToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            System.out.println("Token not found!");
            throw new RuntimeException("Invalid verification token");
        }

        VerificationToken verificationToken = optionalToken.get();
        User user = verificationToken.getUser();

        if (user == null) {
            throw new RuntimeException("User not found for this token");
        }

        user.setVerified(true);
        userRepository.save(user);

        // Delete token after successful verification
        tokenRepository.delete(verificationToken);

        System.out.println("User " + user.getEmail() + " verified successfully");
    }

    @Override
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }


        // Build UserDto from User entity
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(String.valueOf(user.getRole()));

        // Generate token using JwtAuthenticationProvider
        return jwtAuthenticationProvider.generateToken(userDto);
    }
}
