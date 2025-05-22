package taskmanager.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import taskmanager.api.model.Priority;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private Priority priority;
    private int estimatedDuration;
    private LocalDateTime deadline;
    private Long projectId;
    private Long assignedUserId;
}
