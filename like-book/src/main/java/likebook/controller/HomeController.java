package likebook.controller;

import likebook.model.dto.post.PostWithUsernamesDTO;
import likebook.model.dto.song.SongsByGenreDTO;
import likebook.model.entity.post.Post;
import likebook.model.entity.user.User;
import likebook.service.PostService;
import likebook.service.UserService;
import likebook.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/")
public class HomeController {
    private final LoggedUser loggedUser;
    private final PostService postService;
    private final UserService userService;

    public HomeController(LoggedUser loggedUser,
                              PostService postService,
                              UserService userService) {
        this.loggedUser = loggedUser;
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public String index() {
        if (loggedUser.isLogged()) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (!loggedUser.isLogged()) {
            return "redirect:/";
        }
        User user = userService.findUserById(loggedUser.getId()).orElse(null);

        model.addAttribute("currentUserInfo", user);
        Set<Post> postsFromCurrentUser = this.postService.getPostsFromCurrentUser(this.loggedUser.getId());
        model.addAttribute("userPosts", postsFromCurrentUser);
        Set<PostWithUsernamesDTO> postsFromOtherUsers = this.postService.getPostsFromOtherUsers(this.loggedUser.getId());
        model.addAttribute("otherUserPosts", postsFromOtherUsers);
        model.addAttribute("user", user);

        return "home";
    }


    @ModelAttribute
    public SongsByGenreDTO songs() {
        return new SongsByGenreDTO();
    }
}

