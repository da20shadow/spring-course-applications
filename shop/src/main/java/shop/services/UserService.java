package shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.models.dtos.user.UserDTO;
import shop.models.entities.User;
import shop.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public UserDTO getUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return null;
        }
        return this.mapUserToUserDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return null;
        }
        return this.mapUserToUserDTO(user);
    }

    private UserDTO mapUserToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public User getProfileInfo(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }
}
