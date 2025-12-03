package com.store_api.controllers;

import com.store_api.dtos.UserDto;
import com.store_api.mappers.UserMapper;
import com.store_api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping("")
    public Iterable<UserDto.UserInfo> getAllUsers(@RequestParam(required = false, defaultValue = "", name = "sort") String sortBy) {
        if (!Set.of("name", "email").contains(sortBy))
            sortBy = "name";

        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDto)
                .toList();
        /**
         * .map(user -> new UserDto.UserInfo(user.getId(), user.getName(), user.getEmail()))
         * => .map(userMapper::toDto)
         */
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto.UserInfo> getUserById(@PathVariable Long id){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build(); // STATUS: 404 Not Found
        }

        return ResponseEntity.ok(userMapper.toDto(user)); // STATUS: 200 OK
    }

    @PostMapping("")
    public ResponseEntity<UserDto.UserInfo> createUser(@RequestBody UserDto.RegisterUserRequest request, UriComponentsBuilder uriBuilder){
        var user = userMapper.toEntity(request);
        userRepository.save(user);

        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        /**
         * location of the new object/resource that is created
         * ex: localhost:8080/users/5
         */

        return ResponseEntity.created(uri).body(userDto); // STATUS: 201 Created
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto.UserInfo> updateUser(@PathVariable(name = "id") Long id, @RequestBody UserDto.UpdateUserRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build(); // STATUS: 404 Not Found
        }

        userMapper.update(request, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user)); // STATUS: 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build(); // STATUS: 404 Not Found
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build(); // STATUS: 204 No Content
    }

    @PostMapping("/{id}/change-password") // Use Post: not only updating but performing an action
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody UserDto.ChangePasswordRequest request){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build(); // STATUS: 404 Not Found
        }

        if (!user.getPassword().equals(request.getOldPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // STATUS: 401 Unauthorized
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.noContent().build(); // STATUS: 204 No Content
    }
}
