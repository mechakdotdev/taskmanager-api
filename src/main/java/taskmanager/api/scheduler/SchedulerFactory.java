package taskmanager.api.scheduler;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SchedulerFactory {

    private final Map<String, Scheduler> schedulers;

    public SchedulerFactory(List<Scheduler> schedulerList) {
        this.schedulers = schedulerList.stream()
                .collect(Collectors.toMap(Scheduler::getName, scheduler -> scheduler));
    }

    public Optional<Scheduler> getScheduler(String name) {
        return Optional.ofNullable(schedulers.get(name));
    }
}
