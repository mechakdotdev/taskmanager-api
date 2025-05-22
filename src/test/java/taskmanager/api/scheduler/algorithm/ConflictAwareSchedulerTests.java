package taskmanager.api.scheduler.algorithm;

import taskmanager.api.model.Priority;
import taskmanager.api.model.Task;
import taskmanager.api.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConflictAwareSchedulerTests {

    private ConflictAwareScheduler scheduler;
    private User testUser;
    LocalDateTime fixedDateTime = LocalDateTime.of(1990, 1, 1, 0, 0);
    Clock fixedClock = Clock.fixed(fixedDateTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

    @BeforeEach
    void setUp() {

        scheduler = new ConflictAwareScheduler(fixedClock);
        testUser = getMockUser();
    }

    @Test
    void should_ExcludeTask_When_TaskExceedsTimeCapacity() {
        var mockTask1 = buildMockTask("Task 1", Priority.HIGH, 2, 5);
        var mockTask2 = buildMockTask("Task 2", Priority.MEDIUM, 3, 8);
        var invalidTask = buildMockTask("Task 3 - Duration too long and deadline outside of allowed time",
                Priority.HIGH,
                4,
                6);

        List<Task> scheduledTasks = scheduler.schedule(List.of(mockTask1, mockTask2, invalidTask));

        assertThat(scheduledTasks)
                .hasSize(2)
                .extracting(Task::getTitle)
                .containsExactly("Task 1", "Task 2");
    }

    @Test
    void should_ExcludeTask_When_TaskExceedsUserCapacity() {
        var mockTask = buildMockTask("Short Task", Priority.HIGH, 3, 10);
        var invalidTask = buildMockTask("Task - Too long", Priority.LOW, 4, 12);

        List<Task> scheduledTasks = scheduler.schedule(List.of(mockTask, invalidTask));

        assertThat(scheduledTasks)
                .hasSize(1)
                .extracting(Task::getTitle)
                .containsExactly("Short Task");
    }

    @Test
    void should_ExcludeTask_When_DeadlineExpired() {
        var expiredTask = buildMockTask("Expired Task", Priority.HIGH, 1, -1);

        List<Task> scheduledTasks = scheduler.schedule(List.of(expiredTask));

        assertThat(scheduledTasks).isEmpty();
    }

    private User getMockUser() {
        return User.builder()
                .id(1L)
                .name("mock-user")
                .capacity(5)
                .build();
    }

    private Task buildMockTask(String title, Priority priority, int estimatedDuration, int deadlineOffsetHours) {
        return Task.builder()
                .title(title)
                .priority(priority)
                .estimatedDuration(estimatedDuration)
                .deadline(fixedDateTime.plusHours(deadlineOffsetHours))
                .assignedUser(testUser)
                .build();
    }
}
