package com.permission.api.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile_system")
public class ProfileSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_system_seq")
    @SequenceGenerator(name = "profile_system_seq", sequenceName = "profile_system_id_seq", allocationSize = 1)
    @Column(name = "id_profile_system")
    private Long idProfileSystem;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_system", nullable = false)
    private SystemEntity system;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "status", length = 20)
    private String status;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
    }
}
