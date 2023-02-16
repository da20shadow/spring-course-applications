package pathfinder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pathfinder.models.dto.route.view.MostCommentedRouteViewDTO;
import pathfinder.models.entity.Route;
import pathfinder.repositories.RouteRepository;

import java.util.NoSuchElementException;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public MostCommentedRouteViewDTO getMostCommented(){
        Route route = this.routeRepository.findMostCommented()
                .orElseThrow(NoSuchElementException::new)
                .get(0);
        return MostCommentedRouteViewDTO.fromRoute(route);
    }
}
