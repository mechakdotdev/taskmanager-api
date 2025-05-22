package taskmanager.api.authentication;

import taskmanager.api.model.User;
import taskmanager.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MockAuthenticationService implements AuthenticationService {

    private final UserRepository userRepository;

    @Value("${app.authentication.mock-user-id}")
    private Long mockUserId;

    @Override
    public Optional<User> getAuthenticatedUser() {
        return userRepository.findById(mockUserId);
    }

    @Override
    public boolean isAuthenticated() {
        return userRepository.existsById(mockUserId);
    }
}
