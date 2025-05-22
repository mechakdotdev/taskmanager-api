package taskmanager.api.controller;

import taskmanager.api.model.Task;
import taskmanager.api.service.SchedulerService;
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
public class SchedulingController {

    private final SchedulerService schedulerService;

    @GetMapping
    public ResponseEntity<List<Task>> getScheduledTasks(@RequestParam String algorithm) {
        List<Task> result = schedulerService.scheduleTasks(algorithm);
        return ResponseEntity.ok(result);
    }
}
