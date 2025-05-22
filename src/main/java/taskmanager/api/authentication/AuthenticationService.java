package taskmanager.api.authentication;

import taskmanager.api.model.User;

import java.util.Optional;

public interface AuthenticationService {
    Optional<User> getAuthenticatedUser();

    boolean isAuthenticated();
}
