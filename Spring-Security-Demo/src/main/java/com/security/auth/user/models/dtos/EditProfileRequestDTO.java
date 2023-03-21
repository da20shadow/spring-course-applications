package com.security.auth.user.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileRequestDTO {
    private String firstName;
    private String email;
    private String password;
}
