package com.productivity.user.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Size(min = 2, max = 45)
    private String firstName;

    @NotBlank
    @Email
    @Size(min = 10, max = 145)
    private String email;

    @NotBlank
    @Size(min = 8, max = 45)
    private String password;
}
