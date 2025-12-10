package com.store_api.services;

import com.store_api.entities.User;
import com.store_api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

     public User getCurrentUser() {
         var auth = SecurityContextHolder.getContext().getAuthentication();
         var userId = (Long) auth.getPrincipal();

         return userRepository.findById(userId).orElse(null);
     }
}
