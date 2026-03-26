package com.neemroz.controller;

import com.neemroz.model.NewsletterSubscriber;
import com.neemroz.repository.NewsletterSubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/newsletter")
@RequiredArgsConstructor
public class NewsletterController {

    private final NewsletterSubscriberRepository repo;

    // PUBLIC: subscribe
    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody Map<String, String> body) {
        String email = body.getOrDefault("email", "").trim().toLowerCase();
        if (email.isEmpty() || !email.contains("@")) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid email address"));
        }
        if (repo.existsByEmail(email)) {
            return ResponseEntity.ok(Map.of("message", "already_subscribed"));
        }
        NewsletterSubscriber sub = NewsletterSubscriber.builder().email(email).build();
        repo.save(sub);
        return ResponseEntity.ok(Map.of("message", "subscribed"));
    }

    // ADMIN: get all subscribers
    @GetMapping("/subscribers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NewsletterSubscriber>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    // ADMIN: delete subscriber
    @DeleteMapping("/subscribers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "deleted"));
    }
}
