package exam2023.services;

import exam2023.models.dtos.user.UserDTO;
import exam2023.models.entities.User;
import exam2023.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
