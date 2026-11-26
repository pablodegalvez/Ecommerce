package com.online.tienda.ecommerce.controller;


import com.online.tienda.ecommerce.dto.AuthResponse;
import com.online.tienda.ecommerce.dto.LoginRequest;
import com.online.tienda.ecommerce.dto.RegisterRequest;
import com.online.tienda.ecommerce.entity.Role;
import com.online.tienda.ecommerce.entity.User;
import com.online.tienda.ecommerce.enums.RoleType;
import com.online.tienda.ecommerce.repository.RoleRepository;
import com.online.tienda.ecommerce.repository.UserRepository;
import com.online.tienda.ecommerce.security.JwtProvider;
import com.online.tienda.ecommerce.security.UserAdapter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        //authenticationManager es capaz de encontrar el UserDetailsService y el Bean BCryptPasswordEncoder y usarlos a través de un AuthenticationProvider.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);

        UserAdapter userDetails = (UserAdapter) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(new AuthResponse(jwt, "Bearer", userDetails.getEmail(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.email())) {
            return ResponseEntity.badRequest().body(Map.of("message", "This email address is already in use"));
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

        // 4. Guardar en PostgreSQL
        userRepository.save(user);
        return new ResponseEntity<>(Map.of("mensaje", "Usuario registrado exitosamente"), HttpStatus.CREATED);
    }

}
