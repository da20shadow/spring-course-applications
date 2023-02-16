package likebook.repository;

import likebook.model.entity.mood.Mood;
import likebook.model.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    Set<Post> findByMood(Mood mood);

    Set<Post> findAllByUserId(Long user_id);

    Set<Post> findPostsByUserIdNot(Long user_id);

    //    Optional<Post> findByPerformer(String performer);

}
