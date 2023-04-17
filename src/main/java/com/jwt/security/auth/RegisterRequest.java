package com.jwt.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull(message = "Firstname cannot be empty")
    @Size(min = 1, max = 30)
    private String firstName;

    @NotNull(message = "Lastname cannot be empty")
    private String lastName;

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Password cannot be empty")
    private String password;
}
