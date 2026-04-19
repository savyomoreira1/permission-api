package com.permission.api.domain.repository;

import com.permission.api.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findBySystem_IdSystem(Long idSystem);
}
