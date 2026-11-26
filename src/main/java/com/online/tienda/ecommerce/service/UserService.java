package com.online.tienda.ecommerce.service;


import com.online.tienda.ecommerce.dto.AuthResponse;
import com.online.tienda.ecommerce.dto.RegisterRequest;
import com.online.tienda.ecommerce.entity.Role;
import com.online.tienda.ecommerce.entity.User;
import com.online.tienda.ecommerce.enums.RoleType;
import com.online.tienda.ecommerce.exception.EmailAlreadyExistsException;
import com.online.tienda.ecommerce.repository.RoleRepository;
import com.online.tienda.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public void registerUser (RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new EmailAlreadyExistsException("This email address is already in use");
        }

        User user = new User();
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));

        Set<Role> roles = new HashSet<>();
        Role roleUser = roleRepository.findByRoleType(RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: This role is not found"));
        roles.add(roleUser);

        user.setRoles(roles);

        userRepository.save(user);

    }
}
