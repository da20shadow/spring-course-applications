package com.security.auth.user.models.dtos;

import com.security.auth.user.models.enums.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {
    private String firstName;
    private String email;
    private UserRole role;
}
