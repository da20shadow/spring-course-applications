package pathfinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pathfinder.models.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findOneByUsername(String username);
    Optional<User> findOneByEmail(String email);
    Optional<User> findOneByUsernameAndPassword(String username, String password);
}
