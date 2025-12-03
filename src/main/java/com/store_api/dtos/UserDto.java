package com.store_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class UserDto {
    @Getter @Setter
    @AllArgsConstructor
    public static class UserInfo {
        // @JsonIgnore 해당 variable을 무시한다
        // @JsonProperty("user_id") 보여주는 variable 명을 바꿈
        private Long id;
        private String name;
        private String email;
    }

    @Getter @Setter
    public static class RegisterUserRequest {
        @NotBlank(message = "Name is required.")
        @Size(max = 255, message = "Name must be less than 255 characters.")
        private String name;

        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        private String email;

        // @JsonInclude(JsonInclude.Include.NON_NULL) NULL 값을 json에 표시되지 않게 해준다
        @NotBlank(message = "Password is required.")
        @Size(min = 6, max = 25, message = "Password must be at between 6 to 25 characters.")
        private String password;
    }

    @Getter @Setter
    public static class UpdateUserRequest {
        private String name;
        private String email;
    }

    @Getter @Setter
    public static class ChangePasswordRequest {
        private String oldPassword;
        private String newPassword;
    }
}
