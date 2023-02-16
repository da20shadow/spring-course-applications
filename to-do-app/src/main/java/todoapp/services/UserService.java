package todoapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todoapp.models.entities.User;
import todoapp.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        Optional<User> user = this.userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    public User getUserByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }
}
