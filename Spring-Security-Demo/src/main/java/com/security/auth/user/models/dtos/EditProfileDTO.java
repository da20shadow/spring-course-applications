package com.security.auth.user.models.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EditProfileDTO {
    private String firstName;
    private String email;
    private String password;
}
