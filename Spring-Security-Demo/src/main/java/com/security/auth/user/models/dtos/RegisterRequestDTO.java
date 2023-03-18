package com.security.auth.user.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    @NotBlank(message = "First name can not be blank!")
    @Size(min = 2, max = 45,message = "First name must be between 2-45 characters long!")
    private String firstName;

    @NotBlank(message = "Email can not be blank!")
    @Size(min = 9, max = 145,message = "Email must be between 9-145 characters long!")
    @Pattern(regexp = "^[a-z]+[a-z0-9_]+[@][a-z]{2,}[.][a-z]{2,}$",message = "Invalid Email format!")
    private String email;

    @NotBlank(message = "Password can not be blank!")
    @Size(min = 6, max = 145,message = "Password must be between 6-145 characters long!")
    private String password;
}
