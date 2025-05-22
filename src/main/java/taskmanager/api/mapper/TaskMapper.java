package taskmanager.api.mapper;

import org.springframework.stereotype.Component;
import taskmanager.api.dto.TaskDto;
import taskmanager.api.model.Task;

@Component
public class TaskMapper {

    public TaskDto toDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .priority(task.getPriority())
                .estimatedDuration(task.getEstimatedDuration())
                .deadline(task.getDeadline())
                .projectId(task.getProject() != null ? task.getProject().getId() : null)
                .assignedUserId(task.getAssignedUser() != null ? task.getAssignedUser().getId() : null)
                .build();
    }
}
