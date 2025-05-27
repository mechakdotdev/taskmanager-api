package taskmanager.api.repository;

import taskmanager.api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedUser_IdIn(List<Long> userIds);
}
