package com.permission.api.domain.repository;

import com.permission.api.domain.entity.ProfilePermissionAccount;
import com.permission.api.domain.entity.UserAccountSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfilePermissionAccountRepository extends JpaRepository<ProfilePermissionAccount, Long> {

    List<ProfilePermissionAccount> findByUserAccountSystem(UserAccountSystem userAccountSystem);

    @Query("""
            SELECT ppa FROM ProfilePermissionAccount ppa
            JOIN FETCH ppa.profilePermission pp
            JOIN FETCH pp.permission p
            JOIN FETCH p.system s
            WHERE ppa.userAccountSystem.idUserAccountSystem = :idUserAccountSystem
            AND ppa.status = 'ACTIVE'
            """)
    List<ProfilePermissionAccount> findActivePermissionsByUserAccountSystem(
            @Param("idUserAccountSystem") Long idUserAccountSystem);
}
