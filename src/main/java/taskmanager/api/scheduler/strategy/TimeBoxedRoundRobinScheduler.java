package taskmanager.api.scheduler.strategy;

import org.springframework.stereotype.Component;
import taskmanager.api.model.Task;
import taskmanager.api.model.User;
import taskmanager.api.repository.UserRepository;
import taskmanager.api.scheduler.Scheduler;
import taskmanager.api.scheduler.algorithm.SchedulingAlgorithms;

import java.util.*;

@Component
public class TimeBoxedRoundRobinScheduler implements Scheduler {

    private final UserRepository userRepository;

    public TimeBoxedRoundRobinScheduler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Task> schedule(List<Task> tasks) {
        throw new UnsupportedOperationException("A POST method with userIds is required for the algorithm " + getName());
    }

    @Override
    public List<Task> schedule(List<Task> tasks, List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        Map<User, Integer> userCapacities = new HashMap<>();
        users.forEach(user -> userCapacities.put(user, user.getCapacity()));

        List<Task> assignedTasks = new ArrayList<>();
        Queue<Task> taskQueue = new LinkedList<>(tasks);
        int userIndex = 0;

        while (!taskQueue.isEmpty() && !users.isEmpty()) {
            Task task = taskQueue.poll();
            User user = users.get(userIndex);

            int remainingCapacity = userCapacities.getOrDefault(user, 0);
            if (remainingCapacity >= task.getEstimatedDuration()) {
                task.setAssignedUser(user);
                assignedTasks.add(task);
                userCapacities.put(user, remainingCapacity - task.getEstimatedDuration());
            }

            userIndex = (userIndex + 1) % users.size();
        }

        return assignedTasks;
    }

    @Override
    public String getName() {
        return SchedulingAlgorithms.ROUND_ROBIN;
    }
}
