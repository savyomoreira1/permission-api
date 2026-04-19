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
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_seq")
    @SequenceGenerator(name = "permission_seq", sequenceName = "permission_id_seq", allocationSize = 1)
    @Column(name = "id_permission")
    private Long idPermission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_system", nullable = false)
    private SystemEntity system;

    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "description", length = 255)
    private String description;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
    }
}
