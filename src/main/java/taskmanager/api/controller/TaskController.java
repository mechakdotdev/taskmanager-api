package taskmanager.api.controller;

import taskmanager.api.dto.TaskDto;
import taskmanager.api.mapper.TaskMapper;
import taskmanager.api.model.Task;
import taskmanager.api.service.scheduler.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class TaskController {

    private final SchedulerService schedulerService;
    private final TaskMapper taskMapper;

    @GetMapping()
    public ResponseEntity<List<TaskDto>> getScheduledTasks(@RequestParam String algorithm) {
        List<Task> scheduledTasks = schedulerService.scheduleTasks(algorithm);
        List<TaskDto> dtoList = scheduledTasks.stream()
                .map(taskMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtoList);
    }
}
