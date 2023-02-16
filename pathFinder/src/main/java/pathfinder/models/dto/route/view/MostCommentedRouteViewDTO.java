package pathfinder.models.dto.route.view;

import pathfinder.models.entity.Route;

import java.util.NoSuchElementException;

public class MostCommentedRouteViewDTO {
    private String name;
    private String description;
    private String imgUrl;

    public MostCommentedRouteViewDTO() {
    }

    public MostCommentedRouteViewDTO(String name, String description, String imgUrl) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public MostCommentedRouteViewDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MostCommentedRouteViewDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public MostCommentedRouteViewDTO setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public static MostCommentedRouteViewDTO fromRoute(Route route) {
        return new MostCommentedRouteViewDTO(route.getName(), route.getDescription(),
                route.getPictures()
                        .stream()
                        .findFirst()
                        .orElseThrow(NoSuchElementException::new).getUrl());
    }
}
