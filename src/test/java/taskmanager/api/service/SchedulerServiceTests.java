package taskmanager.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import taskmanager.api.model.Task;
import taskmanager.api.repository.TaskRepository;
import taskmanager.api.scheduler.Scheduler;
import taskmanager.api.scheduler.SchedulerResolver;
import taskmanager.api.service.scheduler.SchedulerService;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static taskmanager.api.utils.TestHelper.getAllAlgorithmNames;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SchedulerResolver schedulerResolver;

    @Mock
    private Scheduler mockScheduler;

    private SchedulerService schedulerService;

    @BeforeEach
    void setUp() {
        schedulerService = new SchedulerService(taskRepository, schedulerResolver);
    }

    @ParameterizedTest
    @MethodSource("allAlgorithmNames")
    void should_ScheduleTasksUsingResolvedScheduler(String algorithm) {
        // Arrange
        var mockTasks = List.of(new Task());
        var scheduledTasks = List.of(new Task());

        when(taskRepository.findAll()).thenReturn(mockTasks);
        when(schedulerResolver.resolve(algorithm)).thenReturn(mockScheduler);
        when(mockScheduler.schedule(mockTasks)).thenReturn(scheduledTasks);

        // Act
        var result = schedulerService.scheduleTasks(algorithm);

        // Assert
        assertEquals(scheduledTasks, result);
        verify(taskRepository, times(1)).findAll();
        verify(schedulerResolver, times(1)).resolve(algorithm);
        verify(mockScheduler, times(1)).schedule(mockTasks);
    }

    static Stream<String> allAlgorithmNames() { return getAllAlgorithmNames(); }
}