package io.paideia.backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_readings")
public class UserReading {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private ReadingGroup group;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(length = 20)
    private String status = "IN_PROGRESS";

    @OneToMany(mappedBy = "userReading", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingProgress> progresses;

    public UserReading() {}
}
