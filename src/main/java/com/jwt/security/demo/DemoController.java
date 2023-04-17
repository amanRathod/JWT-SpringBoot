package com.jwt.security.demo;

import com.jwt.security.exception.UserNotFoundException;
import com.jwt.security.user.User;
import com.jwt.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

    private final UserRepository repository;

    @GetMapping("/health-check")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public User getUser(@PathVariable Integer id) throws UserNotFoundException {
        User user = this.repository.findByEmail("an@gmail.com")
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return user;
    }

}
