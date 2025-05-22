package taskmanager.api.controller;

import taskmanager.api.authentication.AuthenticationService;
import taskmanager.api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mock-auth")
@RequiredArgsConstructor
public class MockAuthenticationController {

    private final AuthenticationService authService;

    @GetMapping("/current-user")
    public ResponseEntity<User> getAuthenticatedUser() {
        return authService.getAuthenticatedUser()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}
