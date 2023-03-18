package com.security.auth.user.models.dtos;

import com.security.auth.user.models.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EditProfileSuccessDTO {
    private String message;
    private String email;
    private String token;
    private UserRole role;
}
