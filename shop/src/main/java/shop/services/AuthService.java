package shop.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.constants.Messages;
import shop.exceptions.BadRequestException;
import shop.models.dtos.user.LoginUserDTO;
import shop.models.dtos.user.RegisterUserDTO;
import shop.models.entities.User;
import shop.repositories.UserRepository;
import shop.utils.LoggedUser;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggedUser loggedUser;
    private final HttpSession session;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, LoggedUser loggedUser, HttpSession session) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggedUser = loggedUser;
        this.session = session;
    }

    public void login(LoginUserDTO loginUserDTO) {
        boolean validCredentials = this.checkCredentials(loginUserDTO.getUsername(), loginUserDTO.getPassword());

        System.out.println("validCredentials: " + validCredentials);

        if (!validCredentials) {
            throw new BadRequestException(Messages.INVALID_CREDENTIALS);
        }

        User user = this.userRepository.findByUsername(loginUserDTO.getUsername()).orElse(null);
        assert user != null;
        this.loggedUser.setId(user.getId());
        this.loggedUser.setUsername(user.getUsername());
    }

    public void register(RegisterUserDTO registerUserDTO) {
        if (!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())) {
            throw new BadRequestException(Messages.PASSWORD_MISMATCH);
        }
        this.userRepository.save(this.mapUserDtoToUser(registerUserDTO));
    }

    public void logout() {
        //TODO:
        this.session.invalidate();
        this.loggedUser.clear();
    }

    private User mapUserDtoToUser(RegisterUserDTO registerUserDTO) {
        User user = new User();
        user.setUsername(registerUserDTO.getUsername());
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(this.passwordEncoder.encode(registerUserDTO.getPassword()));
        return user;
    }

    public boolean checkCredentials(String username, String password) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return false;
        }
        System.out.println("Username: " + user.getUsername());

        return this.passwordEncoder.matches(password, user.getPassword());
    }
}
