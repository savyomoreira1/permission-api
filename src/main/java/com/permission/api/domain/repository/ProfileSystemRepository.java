package com.permission.api.domain.repository;

import com.permission.api.domain.entity.ProfileSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileSystemRepository extends JpaRepository<ProfileSystem, Long> {

    List<ProfileSystem> findBySystem_IdSystem(Long idSystem);
}
