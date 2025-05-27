package taskmanager.api.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import taskmanager.api.dto.TaskDto;
import taskmanager.api.mapper.TaskMapper;
import taskmanager.api.model.Task;
import taskmanager.api.model.request.ScheduleRequest;
import taskmanager.api.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;



@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class SchedulerController {

    private final SchedulerService schedulerService;
    private final TaskMapper taskMapper;

    @SneakyThrows
    @GetMapping()
    public ResponseEntity<List<TaskDto>> getScheduledTasks(@RequestParam String algorithm, @RequestParam Long userId) {

        if (userId <= 0) {
            throw new IllegalArgumentException(
                    String.format("A valid user ID is required for the scheduling strategy '%s'", algorithm));
        }
        List<Task> scheduledTasks = schedulerService.schedule(algorithm, userId);
        List<TaskDto> dtoList = scheduledTasks.stream()
                .map(taskMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    public ResponseEntity<List<TaskDto>> getScheduledTasksWithPayload(
            @RequestParam String algorithm,
            @RequestBody ScheduleRequest request
    ) {

        List<Long> userIds = request.getUsers();

        if (userIds.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("A user ID is required for the scheduling strategy '%s'", algorithm));
        }

        List<Task> scheduledTasks = schedulerService.schedule(algorithm, userIds);
        List<TaskDto> dtoList = scheduledTasks.stream()
                .map(taskMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtoList);
    }
}
