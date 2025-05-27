package taskmanager.api.service;

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

    public List<Task> schedule(String algorithm, Long userId) {
        var tasks = taskRepository.findAll().stream().filter(
                task -> task.getAssignedUser().getId().equals(userId)).toList();

        Scheduler scheduler = schedulerResolver.resolve(algorithm);

        return scheduler.schedule(tasks);
    }

    public List<Task> schedule(String algorithm, List<Long> userIds) {
        List<Task> tasks = taskRepository.findByAssignedUser_IdIn(userIds);

        Scheduler scheduler = schedulerResolver.resolve(algorithm);

        return scheduler.schedule(tasks, userIds);
    }
}
