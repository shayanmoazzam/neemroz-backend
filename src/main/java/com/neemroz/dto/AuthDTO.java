package com.neemroz.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

public class AuthDTO {

    @Data
    public static class RegisterRequest {
        @NotBlank
        private String firstName;

        @NotBlank
        private String lastName;

        @Email
        @NotBlank
        private String email;

        @NotBlank
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;

        private String phone;
    }

    @Data
    public static class LoginRequest {
        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }

    @Data
    public static class AuthResponse {
        private String token;
        private Long userId;
        private String firstName;
        private String lastName;
        private String email;
        private String role;

        public AuthResponse(String token, Long userId, String firstName,
                            String lastName, String email, String role) {
            this.token = token;
            this.userId = userId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.role = role;
        }
    }
}
