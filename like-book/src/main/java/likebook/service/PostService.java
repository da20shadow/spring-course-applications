package likebook.service;

import likebook.model.dto.post.AddPostDTO;
import likebook.model.dto.post.PostWithUsernamesDTO;
import likebook.model.entity.mood.Mood;
import likebook.model.entity.post.Post;
import likebook.model.entity.user.User;
import likebook.model.enums.MoodsEnum;
import likebook.repository.PostRepo;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepo repo;
    private final MoodService moodService;
    private final UserService userService;

    public PostService(PostRepo repo, MoodService moodService,UserService userService) {
        this.repo = repo;
        this.moodService = moodService;
        this.userService = userService;
    }

    public void addPost(AddPostDTO addPostDTO) {
        this.repo.save(this.mapPost(addPostDTO));
    }


    private Post mapPost(AddPostDTO addPostDTO) {
        Post post = new Post();
        Mood mood = this.moodService.findMood(addPostDTO.getMood());
        User userById = userService.findUserById(addPostDTO.getId()).orElse(null);

        post.setMood(mood);
        post.setContent(addPostDTO.getContent());
        post.setUser(userById);
        return post;
    }

    public Set<Post> getPostsFromCurrentUser(Long id) {
        return repo.findAllByUserId(id);
    }

    public Set<PostWithUsernamesDTO> getPostsFromOtherUsers(Long id) {
        Set<Post> postsByUserIdNot = repo.findPostsByUserIdNot(id);

        return mapToPostWithUsernameDTO(postsByUserIdNot);
    }

    private Set<PostWithUsernamesDTO> mapToPostWithUsernameDTO(Set<Post> postsByUserIdNot) {
        return postsByUserIdNot.stream()
                .map(e -> {
                    PostWithUsernamesDTO currentDTO = new PostWithUsernamesDTO();
                    currentDTO.setContent(e.getContent())
                            .setId(e.getId())
                            .setLikes(e.getLikes())
                            .setUserLikes(e.getUserLikes())
                            .setMood(e.getMood().getMoodName())
                            .setUsername(e.getUser().getUsername());
                    return currentDTO;
                })
                .collect(Collectors.toSet());
    }

    public void likePostWithId(Long postId, Long userId) {
        Post post = repo.findById(postId).orElse(null);

        User user = userService.findUserById(userId).orElse(null);
        post.getUserLikes().add(user);

        post.setLikes(post.getLikes()+1);
        repo.save(post);
    }

    public void addTestPosts() {
        Post adminPost = new Post().setLikes(1)
                .setUser(userService.findUserById(Long.parseLong("1")).orElse(null))
                .setMood(moodService.findMood(MoodsEnum.HAPPY))
                .setContent("Today i have got a promotion!");
        repo.save(adminPost);

        Post adminPost2 = new Post().setLikes(1)
                .setUser(userService.findUserById(Long.parseLong("1")).orElse(null))
                .setMood(moodService.findMood(MoodsEnum.SAD))
                .setContent("My girlfriend lost her keys :(");
        repo.save(adminPost2);

        Post testPost1 = new Post().setLikes(1)
                .setUser(userService.findUserById(Long.parseLong("2")).orElse(null))
                .setMood(moodService.findMood(MoodsEnum.HAPPY))
                .setContent("My son graduated!");
        repo.save(testPost1);

        Post testPost2 = new Post().setLikes(1)
                .setUser(userService.findUserById(Long.parseLong("2")).orElse(null))
                .setMood(moodService.findMood(MoodsEnum.INSPIRED))
                .setContent("I am going to quit smoking");
        repo.save(testPost2);
    }

    public void removePostById(Long id) {
        repo.deleteById(id);
    }

}
