package todoapp.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todoapp.constants.Messages;
import todoapp.exceptions.BadRequestException;
import todoapp.models.dtos.LoginUserDTO;
import todoapp.models.dtos.RegisterUserDTO;
import todoapp.models.entities.User;
import todoapp.models.helpers.LoggedUser;
import todoapp.repositories.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final LoggedUser loggedUser;

    @Autowired
    public AuthService(UserRepository userRepository, UserService userService, ModelMapper modelMapper, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.loggedUser = loggedUser;
    }

    public User login(LoginUserDTO loginUserForm) {

        User user = this.userService.getUserByUsername(loginUserForm.getUsername());

        if (user == null) {
            throw new BadRequestException(Messages.INVALID_CREDENTIALS);
        }

        if (!user.getPassword().equals(loginUserForm.getPassword())) {
            throw new BadRequestException(Messages.INVALID_CREDENTIALS);
        }

        this.loggedUser.setId(user.getId());

        return user;
    }

    public void register(RegisterUserDTO registerUserForm) {
        var userFromDb = this.userService.getUserByUsername(registerUserForm.getUsername());

        if (userFromDb != null) {
            throw new BadRequestException(Messages.USERNAME_TAKEN);
        }

        var userByEmail = this.userService.getUserByEmail(registerUserForm.getEmail());

        if (userByEmail != null) {
            throw new BadRequestException(Messages.EMAIL_TAKEN);
        }

        User user = this.modelMapper.map(registerUserForm, User.class);
        user = this.userRepository.save(user);

        System.out.println("New user created with id: " + user.getId());
    }


    public void logoutUser() {
        loggedUser.clearUser();
    }
}
