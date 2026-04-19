package com.permission.api.domain.repository;

import com.permission.api.domain.entity.ProfilePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfilePermissionRepository extends JpaRepository<ProfilePermission, Long> {

    List<ProfilePermission> findBySystem_IdSystem(Long idSystem);
}
