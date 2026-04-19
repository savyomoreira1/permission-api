package com.permission.api.domain.repository;

import com.permission.api.domain.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByIdUserAndIdAccount(Long idUser, Long idAccount);
}
