package com.security.auth.user.services;

import com.security.auth.jwt.JwtService;
import com.security.auth.jwt.JwtTokenRepository;
import com.security.auth.jwt.JwtTokenType;
import com.security.auth.jwt.Token;
import com.security.auth.user.constants.UserMessages;
import com.security.auth.user.exceptions.EmailAlreadyRegisteredException;
import com.security.auth.user.exceptions.InvalidPasswordException;
import com.security.auth.user.exceptions.UserException;
import com.security.auth.user.exceptions.UserNotFoundException;
import com.security.auth.user.models.dtos.LoginRequestDTO;
import com.security.auth.user.models.dtos.LoginSuccessResponseDTO;
import com.security.auth.user.models.dtos.RegisterRequestDTO;
import com.security.auth.user.models.entities.User;
import com.security.auth.user.models.enums.UserRole;
import com.security.auth.user.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequestDTO request) throws EmailAlreadyRegisteredException {
        // Check if the email is already registered
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyRegisteredException(UserMessages.ErrorMessages.EMAIL_ALREADY_REGISTERED);
        }

        // Create a new user entity from the register request DTO
        var user = User.builder()
                .firstName(request.getFirstName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();

        try {
            // Save the new user to the UserRepository
            userRepository.save(user);
            return UserMessages.SuccessMessages.REGISTRATION_SUCCESS;
        } catch (Exception e) {
            throw new UserException(UserMessages.ErrorMessages.REGISTRATION_ERROR);
        }
    }

    public LoginSuccessResponseDTO login(LoginRequestDTO request) throws UserNotFoundException, InvalidPasswordException {

        //Check if the user exist
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ErrorMessages.LOGIN_BAD_CREDENTIALS));

        //Verify the user password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException(UserMessages.ErrorMessages.LOGIN_BAD_CREDENTIALS);
        }
        //Generate jwt token
        var jwtToken = jwtService.generateToken(user);
        jwtService.revokeAllUserTokens(user);
        jwtService.saveUserToken(user, jwtToken);

        return new LoginSuccessResponseDTO(
                UserMessages.SuccessMessages.LOGIN_SUCCESS,
                jwtToken,
                user.getFirstName(),
                user.getEmail(),
                user.getRole());
    }


}

