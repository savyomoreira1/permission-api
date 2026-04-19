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
@Table(name = "profile_permission")
public class ProfilePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_permission_seq")
    @SequenceGenerator(name = "profile_permission_seq", sequenceName = "profile_permission_id_seq", allocationSize = 1)
    @Column(name = "id_profile_permission")
    private Long idProfilePermission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_permission", nullable = false)
    private Permission permission;

    @Column(name = "id_profile")
    private Long idProfile;

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
