package exam2023.models.entities;

import exam2023.models.enums.ConditionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conditions")
public class Condition extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ConditionEnum name;

    @Column(nullable = false)
    private String description;
}
