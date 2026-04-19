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
@Table(name = "profile_permission_account")
public class ProfilePermissionAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_permission_account_seq")
    @SequenceGenerator(name = "profile_permission_account_seq", sequenceName = "profile_permission_account_id_seq", allocationSize = 1)
    @Column(name = "id_profile_permission_account")
    private Long idProfilePermissionAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profile_permission", nullable = false)
    private ProfilePermission profilePermission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_account_system", nullable = false)
    private UserAccountSystem userAccountSystem;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
    }
}
