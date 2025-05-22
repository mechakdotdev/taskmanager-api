package taskmanager.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import taskmanager.api.repository.TaskRepository;
import taskmanager.api.scheduler.SchedulerFactory;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SchedulerFactory schedulerFactory;

    private SchedulerService schedulerService;

    @BeforeEach
    void setUp() {

        schedulerService = new SchedulerService(taskRepository, schedulerFactory);
    }

    @Test
    void scheduleTasks_throwsExceptionForUnknownAlgorithm() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());
        when(schedulerFactory.getScheduler("imaginary-algorithm")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> schedulerService.scheduleTasks("imaginary-algorithm")
        );

        assertEquals("Unknown algorithm: imaginary-algorithm", exception.getMessage());
    }
}