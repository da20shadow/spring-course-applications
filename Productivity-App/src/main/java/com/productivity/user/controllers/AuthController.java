package com.productivity.user.controllers;

import com.productivity.user.models.dtos.LoginRequest;
import com.productivity.user.models.dtos.LoginResponse;
import com.productivity.user.models.dtos.RegisterRequest;
import com.productivity.user.models.dtos.RegisterResponse;
import com.productivity.user.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            RegisterResponse response = authService.registerUser(registerRequest);
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            Map<String, String> errorMsg = new HashMap<>();
            errorMsg.put("message", exception.getMessage());
            return ResponseEntity.badRequest().body(errorMsg);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        System.out.println("===============LOGIN================");
        if (result.hasErrors()) {
        System.out.println("===============LOGIN ERRORS================");
            List<String> errors = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        System.out.println("===============LOGIN SUCCESS================");
        try {
            var response = authService.loginUser(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            Map<String, String> errorMsg = new HashMap<>();
            errorMsg.put("message", exception.getMessage());
            return ResponseEntity.badRequest().body(errorMsg);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logoutUser(request,response);
        return ResponseEntity.ok("Logged out successfully");
    }
}
