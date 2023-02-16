package pathfinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pathfinder.models.entity.Route;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route,String> {
    @Query("select r from Route as r order by size(r.comments) desc ")
    Optional<List<Route>> findMostCommented();
}
