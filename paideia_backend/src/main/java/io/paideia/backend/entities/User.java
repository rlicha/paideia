package io.paideia.backend.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public final class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty
    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @NotEmpty
    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @NotEmpty
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingGroup> ownedGroups;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMember> memberships;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserReading> readings;

    public User() {
    }

    public User(String username, String hashedPassword, String email) {
        this.username = username;
        this.passwordHash = hashedPassword;
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }
}
