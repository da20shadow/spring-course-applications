package likebook.service;

import likebook.model.entity.mood.Mood;
import likebook.model.enums.MoodsEnum;
import likebook.repository.MoodRepo;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MoodService {

    private final MoodRepo repo;

    public MoodService(MoodRepo repo) {
        this.repo = repo;
    }

    public void initMoods() {
        if (this.repo.count() != 0) {
            return;
        }

        Arrays.stream(MoodsEnum.values())
                .forEach(s -> {
                    Mood mood = new Mood();
                    mood.setMoodName(s);
                    mood.setDescription("...");

                    this.repo.save(mood);
                });

    }

    public Mood findMood(MoodsEnum moodsEnum) {
        return this.repo.findByMoodName(moodsEnum).orElseThrow();
    }

    public Mood findStyleByStyleName(MoodsEnum styleName) {
        return this.repo.findByMoodName(styleName).orElseThrow();
    }
}
