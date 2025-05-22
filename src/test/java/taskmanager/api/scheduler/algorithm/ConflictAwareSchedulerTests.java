package taskmanager.api.scheduler.algorithm;

import taskmanager.api.model.Priority;
import taskmanager.api.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static taskmanager.api.utils.TestHelper.mockTask;

class ConflictAwareSchedulerTests {

    private ConflictAwareScheduler scheduler;
    LocalDateTime fixedDateTime = LocalDateTime.of(1990, 1, 1, 0, 0);
    Clock fixedClock = Clock.fixed(fixedDateTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

    @BeforeEach
    void setUp() {

        scheduler = new ConflictAwareScheduler(fixedClock);
    }

    @Test
    void should_ExcludeTask_When_TaskExceedsTimeCapacity() {
        // Arrange
        var mockTask1 = mockTask("Task 1", Priority.HIGH, 2, getMockDeadlineWithFixedDateTime(5));
        var mockTask2 = mockTask("Task 2", Priority.MEDIUM, 3, getMockDeadlineWithFixedDateTime(8));
        var invalidTask = mockTask("Task 3 - Duration too long and deadline outside of allowed time",
                Priority.HIGH,
                4,
                getMockDeadlineWithFixedDateTime(6));

        // Act
        List<Task> scheduledTasks = scheduler.schedule(List.of(mockTask1, mockTask2, invalidTask));

        // Assert
        assertThat(scheduledTasks)
                .hasSize(2)
                .extracting(Task::getTitle)
                .containsExactly("Task 1", "Task 2");
    }

    @Test
    void should_ExcludeTask_When_TaskExceedsUserCapacity() {
        // Arrange
        var mockTask = mockTask("Short Task", Priority.HIGH, 3, getMockDeadlineWithFixedDateTime(10));
        var invalidTask = mockTask("Task - Too long", Priority.LOW, 4, getMockDeadlineWithFixedDateTime(12));

        // Act
        List<Task> scheduledTasks = scheduler.schedule(List.of(mockTask, invalidTask));

        // Assert
        assertThat(scheduledTasks)
                .hasSize(1)
                .extracting(Task::getTitle)
                .containsExactly("Short Task");
    }

    @Test
    void should_ExcludeTask_When_DeadlineExpired() {
        // Arrange
        var expiredTask = mockTask("Expired Task", Priority.HIGH, 1, getMockDeadlineWithFixedDateTime(-1));

        // Act
        List<Task> scheduledTasks = scheduler.schedule(List.of(expiredTask));

        // Assert
        assertThat(scheduledTasks).isEmpty();
    }

    private LocalDateTime getMockDeadlineWithFixedDateTime(int deadlineOffsetHours) {
        return fixedDateTime.plusHours(deadlineOffsetHours);
    }
}
