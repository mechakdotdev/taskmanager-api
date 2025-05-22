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

        var scheduledTasks = new ArrayList<Task>();
        var totalScheduledHours = 0;
        LocalDateTime now = LocalDateTime.now(clock);

        for (Task task : sorted) {
            if (canSchedule(task, now, totalScheduledHours)) {
                scheduledTasks.add(task);
                totalScheduledHours += task.getEstimatedDuration();
            }
        }

        return scheduledTasks;
    }

    private boolean canSchedule(Task task, LocalDateTime now, int hoursScheduled) {
        LocalDateTime deadline = task.getDeadline();
        int duration = task.getEstimatedDuration();
        int userCapacity = task.getAssignedUser().getCapacity();

        boolean isPastDeadline = deadline.isBefore(now);
        boolean exceedsCapacity = hoursScheduled + duration > userCapacity;
        boolean exceedsDeadlineWindow = now.plusHours(hoursScheduled + duration).isAfter(deadline);

        return !isPastDeadline && !exceedsCapacity && !exceedsDeadlineWindow;
    }

    @Override
    public String getName() {
        return SchedulingAlgorithms.CONFLICT_AWARE;
    }
}
