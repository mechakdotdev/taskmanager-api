package taskmanager.api.model.request;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleRequest {
    private List<Long> users;
}
