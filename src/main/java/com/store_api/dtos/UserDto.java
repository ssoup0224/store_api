package com.store_api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
        private String name;
        private String email;
        // @JsonInclude(JsonInclude.Include.NON_NULL) NULL 값을 json에 표시되지 않게 해준다
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
