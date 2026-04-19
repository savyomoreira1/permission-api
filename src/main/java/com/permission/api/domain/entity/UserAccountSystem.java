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
@Table(name = "user_account_system")
public class UserAccountSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_account_system_seq")
    @SequenceGenerator(name = "user_account_system_seq", sequenceName = "user_account_system_id_seq", allocationSize = 1)
    @Column(name = "id_user_account_system")
    private Long idUserAccountSystem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_account", nullable = false)
    private UserAccount userAccount;

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
