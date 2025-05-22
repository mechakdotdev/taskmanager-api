package taskmanager.api.scheduler;

import taskmanager.api.model.Task;

import java.util.List;

public interface Scheduler {
    List<Task> schedule(List<Task> tasks);

    String getName();
}
