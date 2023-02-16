package likebook.repository;

import likebook.model.entity.mood.Mood;
import likebook.model.enums.MoodsEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoodRepo extends JpaRepository<Mood, Long> {

    Optional<Mood> findByMoodName(MoodsEnum moodsEnum);

}
