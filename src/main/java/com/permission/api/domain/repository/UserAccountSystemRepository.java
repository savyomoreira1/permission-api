package com.permission.api.domain.repository;

import com.permission.api.domain.entity.UserAccountSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountSystemRepository extends JpaRepository<UserAccountSystem, Long> {

    Optional<UserAccountSystem> findByUserAccount_IdUserAccountAndSystem_IdSystem(
            Long idUserAccount, Long idSystem);
}
