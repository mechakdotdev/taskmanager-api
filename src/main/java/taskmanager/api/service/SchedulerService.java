package taskmanager.api.service;

import taskmanager.api.model.Task;
import taskmanager.api.repository.TaskRepository;
import taskmanager.api.scheduler.Scheduler;
import taskmanager.api.scheduler.SchedulerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final TaskRepository taskRepository;
    private final SchedulerFactory schedulerFactory;

    public List<Task> scheduleTasks(String algorithmName) {
        List<Task> tasks = taskRepository.findAll();

        Scheduler scheduler = schedulerFactory
                .getScheduler(algorithmName)
                .orElseThrow(() -> new IllegalArgumentException("Unknown algorithm: " + algorithmName));

        return scheduler.schedule(tasks);
    }
}
