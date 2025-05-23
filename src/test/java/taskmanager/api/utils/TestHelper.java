package taskmanager.api.utils;

import taskmanager.api.model.Priority;
import taskmanager.api.model.Task;
import taskmanager.api.model.User;
import taskmanager.api.scheduler.constants.SchedulingAlgorithms;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class TestHelper {
    public static Task mockTask(String title, Priority priority, int estimatedDuration, LocalDateTime deadline) {
        return Task.builder()
                .title(title)
                .priority(priority)
                .estimatedDuration(estimatedDuration)
                .deadline(deadline)
                .assignedUser(mockTestUser())
                .build();
    }

    public static Stream<String> getAllAlgorithmNames() {
        return Stream.of(SchedulingAlgorithms.class.getDeclaredFields())
                .filter(TestHelper::isPublicStaticString)
                .map(TestHelper::getFieldValueSafely);
    }

    private static boolean isPublicStaticString(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers)
                && Modifier.isPublic(modifiers)
                && field.getType().equals(String.class);
    }

    private static String getFieldValueSafely(Field field) {
        try {
            return (String) field.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access field: " + field.getName(), e);
        }
    }

    private static User mockTestUser() {
        return User.builder()
                .id(1L)
                .name("mock-user")
                .capacity(5)
                .build();
    }
}
