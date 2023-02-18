package exam2023.repositories;

import exam2023.models.entities.Condition;
import exam2023.models.enums.ConditionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionRepository extends JpaRepository<Condition, Long> {
    Condition findByName(ConditionEnum name);
}
