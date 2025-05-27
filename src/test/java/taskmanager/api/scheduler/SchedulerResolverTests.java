package taskmanager.api.scheduler;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static taskmanager.api.utils.TestHelper.getAllAlgorithmNames;

@ExtendWith(MockitoExtension.class)
class SchedulerResolverTests {

    @Mock
    private SchedulerFactory schedulerFactory;

    @Mock
    private Scheduler mockScheduler;

    private SchedulerResolver schedulerResolver;

    @BeforeEach
    void setUp() {
        schedulerResolver = new SchedulerResolver(schedulerFactory);
    }

    @Test
    void should_ThrowException_When_AlgorithmIsNotRecognised() {

        // Arrange
        String mockAlgorithmName = "imaginary-algorithm";

        // Act
        when(schedulerFactory.getScheduler(mockAlgorithmName)).thenReturn(Optional.empty());

        // Assert
        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> schedulerResolver.resolve(mockAlgorithmName)
        );

        assertEquals("The 'imaginary-algorithm' scheduling algorithm you specified was not recognised.", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("allAlgorithmNames")
    void should_ResolveSchedulerForEachKnownAlgorithm(String algorithm) {

        // Arrange
        when(schedulerFactory.getScheduler(algorithm)).thenReturn(Optional.of(mockScheduler));

        // Act
        Scheduler resolved = schedulerResolver.resolve(algorithm);

        // Assert
        assertEquals(mockScheduler, resolved);
        verify(schedulerFactory, times(1)).getScheduler(algorithm);
    }

    static Stream<String> allAlgorithmNames() { return getAllAlgorithmNames(); }
}
