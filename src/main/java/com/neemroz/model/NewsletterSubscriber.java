package com.neemroz.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "newsletter_subscribers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsletterSubscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "subscribed_at", nullable = false)
    private LocalDateTime subscribedAt;

    @PrePersist
    protected void onCreate() {
        subscribedAt = LocalDateTime.now();
    }
}
