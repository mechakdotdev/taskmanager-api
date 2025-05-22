package taskmanager.api.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerResolver {

    private final SchedulerFactory schedulerFactory;

    public Scheduler resolve(String algorithm) {
        return schedulerFactory
                .getScheduler(algorithm)
                .orElseThrow(() -> new IllegalArgumentException(String.format("The scheduling algorithm you requested ('%s') was not recognised.", algorithm)));
    }
}
