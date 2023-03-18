package com.security.auth.user.controllers;

import com.security.auth.config.LogoutService;
import com.security.auth.user.models.dtos.EditProfileDTO;
import com.security.auth.user.services.UserService;
import com.security.shared.models.dtos.ErrorResponseDTO;
import com.security.shared.models.dtos.SuccessResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfileInfo(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ResponseEntity.ok(userService.getProfile(userDetails.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @PatchMapping("/profile/edit")
    public ResponseEntity<?> editProfile(@Valid @RequestBody EditProfileDTO editProfileDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            List<String> errorMessages = new ArrayList<>();
            for (FieldError error : errors) {
                errorMessages.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errorMessages.get(0)));
        }

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            return userService.updateProfile(userEmail, editProfileDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

}
