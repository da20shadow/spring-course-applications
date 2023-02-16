package likebook.controller;

import likebook.model.dto.post.AddPostDTO;
import likebook.service.PostService;
import likebook.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final LoggedUser loggedUser;
    private final PostService postService;

    public PostController(LoggedUser loggedUser, PostService postService) {
        this.loggedUser = loggedUser;
        this.postService = postService;
    }
    @GetMapping("/add-post")
    public String addPost() {
        if (!loggedUser.isLogged()) {
            return "redirect:/users/login";
        }
        return "post-add";
    }

    @PostMapping("/add-post")
    public String addPost(AddPostDTO addPostDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("addPostDTO", addPostDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addPostDTO", result);

            return "redirect:/posts/add-post";
        }
        addPostDTO.setId(loggedUser.getId());
        this.postService.addPost(addPostDTO);
        return "redirect:/home";
    }

    @GetMapping("/like-post/{id}")
    public String likePost( Long id) {
        postService.likePostWithId(id, loggedUser.getId());
        return "redirect:/home";
    }

    @PostMapping("/remove/{id}")
    public String removePost( Long id) {
        postService.removePostById(id);
        return "redirect:/home";
    }

    @ModelAttribute
    public AddPostDTO addPostDTO() {
        return new AddPostDTO();
    }
}

