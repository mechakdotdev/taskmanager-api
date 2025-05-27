package taskmanager.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import taskmanager.api.model.Priority;
import taskmanager.api.model.Task;
import taskmanager.api.repository.TaskRepository;
import taskmanager.api.scheduler.Scheduler;
import taskmanager.api.scheduler.SchedulerResolver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static taskmanager.api.utils.TestHelper.*;

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
        var mockUserId = mockUser().getId();
        var tasks = mockTasks();
        var scheduledTasks = mockTasks();

        when(taskRepository.findAll()).thenReturn(tasks);
        when(schedulerResolver.resolve(algorithm)).thenReturn(mockScheduler);
        when(mockScheduler.schedule(tasks)).thenReturn(scheduledTasks);

        // Act
        var result = schedulerService.schedule(algorithm, mockUserId);

        // Assert
        assertEquals(scheduledTasks, result);
        verify(taskRepository, times(1)).findAll();
        verify(schedulerResolver, times(1)).resolve(algorithm);
        verify(mockScheduler, times(1)).schedule(tasks);
    }

    private List<Task> mockTasks() {
        var mockDeadline = LocalDateTime.of(1990, 1, 1, 0, 0);

        var mockTask1 = mockTask("Task 1", Priority.LOW, 2, mockDeadline);
        var mockTask2 = mockTask("Task 2", Priority.MEDIUM, 3, mockDeadline);

        return List.of(mockTask1, mockTask2);
    }

    static Stream<String> allAlgorithmNames() { return getAllAlgorithmNames(); }
}