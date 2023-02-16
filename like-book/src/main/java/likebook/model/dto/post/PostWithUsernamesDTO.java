package likebook.model.dto.post;

import likebook.model.entity.user.User;
import likebook.model.enums.MoodsEnum;

import java.util.Set;

public class PostWithUsernamesDTO {
    private String content;
    private MoodsEnum mood;
    private String username;
    private int likes;
    private Long id;
    private Set<User> userLikes;

    public PostWithUsernamesDTO() {
    }

    public String getContent() {
        return content;
    }

    public PostWithUsernamesDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public MoodsEnum getMood() {
        return mood;
    }

    public PostWithUsernamesDTO setMood(MoodsEnum mood) {
        this.mood = mood;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public PostWithUsernamesDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getLikes() {
        return likes;
    }

    public PostWithUsernamesDTO setLikes(int likes) {
        this.likes = likes;
        return this;
    }

    public Long getId() {
        return id;
    }

    public PostWithUsernamesDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Set<User> getUserLikes() {
        return userLikes;
    }

    public PostWithUsernamesDTO setUserLikes(Set<User> userLikes) {
        this.userLikes = userLikes;
        return this;
    }

    public boolean checkIfUserIdMatchId(Long userId){
        return this.getUserLikes().stream()
                .anyMatch(user -> user.getId().equals(userId));
    }
}

