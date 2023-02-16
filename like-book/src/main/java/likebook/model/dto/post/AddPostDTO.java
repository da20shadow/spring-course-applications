package likebook.model.dto.post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import likebook.model.enums.MoodsEnum;

public class AddPostDTO {
    private Long id;

    @Size(min = 2, max = 50, message = "Content length must be between 2 and 50 characters!")
    @NotNull
    private String content;

    @NotNull(message = "You must select a mood!")

    private MoodsEnum mood;

    public AddPostDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MoodsEnum getMood() {
        return mood;
    }

    public void setMood(MoodsEnum mood) {
        this.mood = mood;
    }

    public String getContent() {
        return content;
    }

    public AddPostDTO setContent(String content) {
        this.content = content;
        return this;
    }
}

