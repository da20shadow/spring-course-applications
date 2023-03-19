package com.security.auth.user.services;

import com.security.auth.jwt.JwtService;
import com.security.auth.user.constants.UserMessages;
import com.security.auth.user.exceptions.InvalidEditProfileDetails;
import com.security.auth.user.exceptions.UserNotFoundException;
import com.security.auth.user.models.dtos.EditProfileDTO;
import com.security.auth.user.models.dtos.EditProfileSuccessDTO;
import com.security.auth.user.models.dtos.UserDTO;
import com.security.auth.user.models.entities.User;
import com.security.auth.user.repositories.UserRepository;
import com.security.shared.models.dtos.SuccessResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserDTO getProfile(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException(UserMessages.ErrorMessages.USER_NOT_FOUND);
        }
        return modelMapper.map(user, UserDTO.class);
    }

    public ResponseEntity<?> updateProfile(String userEmail, EditProfileDTO editProfileDTO) {

        Optional<User> user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new UserNotFoundException(UserMessages.ErrorMessages.USER_NOT_FOUND);
        }
        int changes = 0;
        boolean changedEmail = false;
        if (editProfileDTO.getFirstName() != null && !editProfileDTO.getFirstName().equals(user.get().getFirstName())) {
            user.get().setFirstName(editProfileDTO.getFirstName());
            changes++;
        }
        if (editProfileDTO.getEmail() != null && !editProfileDTO.getEmail().equals(user.get().getEmail())) {
            user.get().setEmail(editProfileDTO.getEmail());
            changes++;
            changedEmail = true;
        }
        if (editProfileDTO.getPassword() != null) {
            user.get().setPassword(passwordEncoder.encode(editProfileDTO.getPassword()));
            changes++;
        }
        if (changes == 0) {
            throw new InvalidEditProfileDetails(UserMessages.ErrorMessages.NOTHING_TO_UPDATE_ERROR);
        }
        var updatedUser = userRepository.save(user.get());

        if (changedEmail) {
            //Re-Generate jwt token
            var jwtToken = jwtService.generateToken(updatedUser);
            jwtService.revokeAllUserTokens(updatedUser);
            jwtService.saveUserToken(updatedUser, jwtToken);

            return ResponseEntity.ok(new EditProfileSuccessDTO(
                    UserMessages.SuccessMessages.UPDATE_SUCCESS,
                    updatedUser.getEmail(),
                    jwtToken,
                    updatedUser.getRole()));
        }
        return ResponseEntity.ok(new SuccessResponseDTO(UserMessages.SuccessMessages.UPDATE_SUCCESS));
    }


}
