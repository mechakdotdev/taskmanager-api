package taskmanager.api.service.scheduler;

import taskmanager.api.model.Task;
import taskmanager.api.repository.TaskRepository;
import taskmanager.api.scheduler.Scheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import taskmanager.api.scheduler.SchedulerResolver;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final TaskRepository taskRepository;
    private final SchedulerResolver schedulerResolver;

    public List<Task> scheduleTasks(String algorithm) {
        List<Task> tasks = taskRepository.findAll();

        Scheduler scheduler = schedulerResolver.resolve(algorithm);

        return scheduler.schedule(tasks);
    }
}
