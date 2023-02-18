package exam2023.services;

import exam2023.models.entities.Condition;
import exam2023.models.enums.ConditionEnum;
import exam2023.repositories.ConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ConditionService {

    private final ConditionRepository conditionRepository;

    @Autowired
    public ConditionService(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    public void insertConditions(){
        if (this.conditionRepository.count() == 0) {
            Arrays.stream(ConditionEnum.values())
                    .forEach(c -> {
                        Condition condition = new Condition();
                        condition.setName(c);
                        condition.setDescription(c.getValue());
                        this.conditionRepository.save(condition);
                    });
        }
    }

    public Condition getConditionByName(ConditionEnum conditionName) {
        return this.conditionRepository.findByName(conditionName);
    }
}
