package com.productivity.user.services;

import com.productivity.config.JwtService;
import com.productivity.config.LogoutService;
import com.productivity.token.Token;
import com.productivity.token.TokenRepository;
import com.productivity.token.TokenType;
import com.productivity.user.models.dtos.LoginRequest;
import com.productivity.user.models.dtos.LoginResponse;
import com.productivity.user.models.dtos.RegisterRequest;
import com.productivity.user.models.dtos.RegisterResponse;
import com.productivity.user.models.entities.User;
import com.productivity.user.models.enums.Role;
import com.productivity.user.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final LogoutService logoutService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterResponse registerUser(RegisterRequest registerRequest) throws Exception {
        RegisterResponse response = new RegisterResponse();

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new Exception("Error: Email is already in use!");
        }

        // Create new user's account
        var user = User.builder()
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        response.setMessage("User registered successfully!");
        return response;
    }

    public LoginResponse loginUser(LoginRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken((UserDetails) user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return LoginResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        logoutService.logout(request,response,null);
    }
}
