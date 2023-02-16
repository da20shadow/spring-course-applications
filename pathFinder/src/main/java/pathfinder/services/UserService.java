package pathfinder.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pathfinder.models.dto.user.UserRegisterDTO;
import pathfinder.models.entity.User;
import pathfinder.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User convertToEntity(UserRegisterDTO userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public void register(User user) {
        this.userRepository.save(user);
    }

    public User getUserByUsername(String username) {

        Optional<User> result = this.userRepository.findOneByUsername(username);

        if (result.isEmpty()) {
            return null;
        }
        return result.get();
    }

    public User getUserByEmail(String email) {
        Optional<User> result = this.userRepository.findOneByEmail(email);

        if (result.isEmpty()) {
            return null;
        }
        return result.get();
    }

    public User login(String username, String password) {
        Optional<User> result = this.userRepository.findOneByUsernameAndPassword(username, password);
        if (result.isEmpty()) {
            return null;
        }
        return result.get();
    }
}
