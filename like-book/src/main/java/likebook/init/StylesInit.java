package likebook.init;

import likebook.service.MoodService;
import likebook.service.PostService;
import likebook.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StylesInit implements CommandLineRunner {

    private final MoodService moodService;
    private final UserService userService;
    private final PostService postService;

    public StylesInit(MoodService moodService,
                      UserService userService,
                      PostService postService) {
        this.moodService = moodService;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public void run(String... args) {
//        this.userService.initAdmin();
//        this.userService.initTest();
//        this.moodService.initMoods();
//        postService.addTestPosts();

    }
}
