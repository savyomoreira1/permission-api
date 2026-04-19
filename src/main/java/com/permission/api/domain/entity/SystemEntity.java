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
@Table(name = "system")
public class SystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_seq")
    @SequenceGenerator(name = "system_seq", sequenceName = "system_id_seq", allocationSize = 1)
    @Column(name = "id_system")
    private Long idSystem;

    @Column(name = "idt_owner")
    private Long idtOwner;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
    }
}
