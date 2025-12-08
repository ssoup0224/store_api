package com.store_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class AuthDto {
    @Getter
    @Setter
    public static class LoginRequest {
        @NotNull(message = "Email is required.")
        @Email
        private String email;
        private String password;
    }
}
