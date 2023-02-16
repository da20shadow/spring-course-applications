package todoapp.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import todoapp.models.entities.User;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String status;
    @NotBlank
    private String description;
    @NotBlank
    private String endDate;
    @NotBlank
    private Long userId;

}
