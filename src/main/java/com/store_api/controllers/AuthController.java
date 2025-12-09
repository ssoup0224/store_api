package com.store_api.controllers;

import com.store_api.dtos.AuthDto;
import com.store_api.dtos.JwtResponseDto;
import com.store_api.dtos.UserDto;
import com.store_api.mappers.UserMapper;
import com.store_api.repositories.UserRepository;
import com.store_api.services.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody AuthDto.LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var token = jwtService.generateToken(request.getEmail());

        return ResponseEntity.ok(new JwtResponseDto(token)); // STATUS: 200 OK
    }

    @PostMapping("/validate")
    public boolean vaildate(@RequestHeader("Authorization") String authHeader) {
        var token = authHeader.replace("Bearer ", "");
        return jwtService.validateToken(token);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto.UserInfo> currentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var email = (String) auth.getPrincipal();

        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build(); // STATUS: 404 Not Found
        }
        var userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto); // STATUS: 200 OK
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
