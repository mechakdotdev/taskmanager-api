package taskmanager.api.scheduler.algorithm;

import taskmanager.api.model.Task;
import taskmanager.api.scheduler.Scheduler;
import org.springframework.stereotype.Component;
import taskmanager.api.scheduler.constants.SchedulingAlgorithms;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class ConflictAwareScheduler implements Scheduler {

    private final Clock clock;

    public ConflictAwareScheduler(Clock clock) {
        this.clock = clock;
    }

    @Override
    public List<Task> schedule(List<Task> tasks) {
        List<Task> sorted = tasks.stream()
                .sorted(Comparator.comparing(Task::getDeadline))
                .toList();

        List<Task> scheduled = new ArrayList<>();
        var hoursToCompleteTasks = 0;
        LocalDateTime now = LocalDateTime.now(clock);

        for (Task task : sorted) {
            var taskDeadline = task.getDeadline();

            if (taskDeadline.isBefore(now)) continue;

            int duration = task.getEstimatedDuration();
            if (hoursToCompleteTasks + duration > task.getAssignedUser().getCapacity()) continue;

            if (now.plusHours(hoursToCompleteTasks + duration).isAfter(taskDeadline)) continue;

            scheduled.add(task);
            hoursToCompleteTasks += duration;
        }

        return scheduled;
    }

    @Override
    public String getName() {
        return SchedulingAlgorithms.CONFLICT_AWARE;
    }
}
