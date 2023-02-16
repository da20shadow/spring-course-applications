package pathfinder.models.entity;

import jakarta.persistence.*;
import pathfinder.models.enums.LEVEL;

import java.util.Set;

@Entity
@Table(name = "routes")
public class Route extends BaseEntity{
    @Column(columnDefinition = "TEXT",name = "gpx_coordinates")
    private String gpx;
    @Enumerated(EnumType.STRING)
    private LEVEL level;
    @Column
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    private User author;
    @Column
    private String url;
    @OneToMany(targetEntity = Comment.class,
            mappedBy = "route",
            cascade = {CascadeType.MERGE,CascadeType.DETACH})
    private Set<Comment> comments;
    @OneToMany(targetEntity = Picture.class,
            mappedBy = "route",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE,CascadeType.DETACH})
    private Set<Picture> pictures;

    public Route() {
    }

    public String getGpx() {
        return gpx;
    }

    public Route setGpx(String gpx) {
        this.gpx = gpx;
        return this;
    }

    public LEVEL getLevel() {
        return level;
    }

    public Route setLevel(LEVEL level) {
        this.level = level;
        return this;
    }

    public String getName() {
        return name;
    }

    public Route setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Route setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Route setComments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public Route setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public Route setAuthor(User author) {
        this.author = author;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Route setUrl(String url) {
        this.url = url;
        return this;
    }
}
